package cn.e3.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3.common.jeids.JedisClient;
import cn.e3.common.utils.E3Result;
import cn.e3.common.utils.JsonUtils;
import cn.e3.dao.TbUserMapper;
import cn.e3.pojo.TbUser;
import cn.e3.pojo.TbUserExample;
import cn.e3.pojo.TbUserExample.Criteria;
import cn.e3.sso.service.TbUserService;

/**
 * 用户管理实现
 * @author Administrator
 *
 */
@Service
public class TbUserServiceImpl implements TbUserService{

	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Autowired
	private JedisClient jedisClient;

	public E3Result checkData(String value, Integer type) {
		//查询数据
		TbUserExample tbUserExample = new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		//动态生成查询条件
		if(type==1){
			criteria.andUsernameEqualTo(value);
		}else if(type==2){
			criteria.andPhoneEqualTo(value);
		}else if(type==3){
			criteria.andEmailEqualTo(value);
		}else{
			E3Result.build(400, "非法参数");
		}
		List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
		//判断是否存在
		if(list==null||list.size()==0){
			return E3Result.ok(true);
		}
		return E3Result.ok(false);
	}
	@Override
	public E3Result addTbUser(TbUser tbUser) {
			// 1、使用TbtbUser接收提交的请求。
			
			if (StringUtils.isBlank(tbUser.getUsername())) {
				return E3Result.build(400, "用户名不能为空");
			}
			if (StringUtils.isBlank(tbUser.getPassword())) {
				return E3Result.build(400, "密码不能为空");
			}
			//校验数据是否可用
			E3Result result = checkData(tbUser.getUsername(), 1);
			if (!(boolean) result.getData()) {
				return E3Result.build(400, "此用户名已经被使用");
			}
			//校验电话是否可以
			if (StringUtils.isNotBlank(tbUser.getPhone())) {
				result = checkData(tbUser.getPhone(), 2);
				if (!(boolean) result.getData()) {
					return E3Result.build(400, "此手机号已经被使用");
				}
			}
			//校验email是否可用
			if (StringUtils.isNotBlank(tbUser.getEmail())) {
				result = checkData(tbUser.getEmail(), 3);
				if (!(boolean) result.getData()) {
					return E3Result.build(400, "此邮件地址已经被使用");
				}
			}
			// 2、补全TbtbUser其他属性。
			tbUser.setCreated(new Date());
			tbUser.setUpdated(new Date());
			// 3、密码要进行MD5加密。
			String md5Pass = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
			tbUser.setPassword(md5Pass);
			// 4、把用户信息插入到数据库中。
			tbUserMapper.insert(tbUser);
			// 5、返回e3Result。
			return E3Result.ok();
		
	}
	@Override
	public E3Result login(String username, String password) {
		//检查用户名密码
		TbUserExample tbUserExample = new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
		if(list==null||list.size()==0){
			return E3Result.build(400, "用户名或密码错误");
		}
		TbUser user =list.get(0);
		if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return E3Result.build(400, "用户名或密码错误");
		}
		//存入redis
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		jedisClient.set("session:"+token,JsonUtils.objectToJson(user));
		jedisClient.expire("session:"+token, 1800);
		return E3Result.ok(token);
		
	}
	@Override
	public E3Result getTbUser(String token) {
		String json = jedisClient.get("session:"+token);
		if(StringUtils.isBlank(json)){
			return E3Result.build(400, "用户登录已过期");
		}
		
		TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
		jedisClient.expire("session:"+token, 1800);
		return E3Result.ok(tbUser);
	}
	@Override
	public void logout(String token) {
		jedisClient.del("session:"+token);
	}

}
