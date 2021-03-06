package rrx.cnuo.cncommon.accessory.context;
import rrx.cnuo.cncommon.vo.LoginUser;

/**
 *    上下文持有对象：将当前登录用户的上下文信息放到本地线程中管理(这样即使获取不到request也可获取到当前登录用户的信息)
 * @author xuhongyu
 * @date 2019年5月31日
 */
public class UserContextHolder {
	
	private static ThreadLocal<LoginUser> userContext = new ThreadLocal<LoginUser>();
	
	public static LoginUser currentUser() {
		return userContext.get();
	}
	public static void set(LoginUser user) {
		userContext.set(user);
	}
	public static void remove() {
		userContext.remove();
	}
}
