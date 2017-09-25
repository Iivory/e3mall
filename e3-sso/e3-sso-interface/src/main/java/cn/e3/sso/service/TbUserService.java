package cn.e3.sso.service;

import cn.e3.common.utils.E3Result;
import cn.e3.pojo.TbUser;

/**
 * 用户管理接口
 * @author Administrator
 *
 */
public interface TbUserService {
	/**
	 * 校验数据
	 * @param value
	 * @param type
	 * @return
	 */
	public E3Result checkData(String value,Integer type);
	
	/**
	 * 注册用户
	 * @param tbUser
	 * @return
	 */
	public E3Result addTbUser(TbUser tbUser);
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	public E3Result login(String username,String password);
	
	/**
	 * 通过token获取用户信息
	 * @param token
	 * @return
	 */
	public E3Result getTbUser(String token);
	
	/**
	 * 登出
	 * @param token
	 * @return
	 */
	public void logout(String token);

}
