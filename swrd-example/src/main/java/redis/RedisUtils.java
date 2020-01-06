package redis;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RedisUtils {
	public static final String ACCOUNT_TICKET_CLUSTER_APP = "accountTicketRedisCluster";
	public static final String ACCOUNT_TICKET_SINGLE_APP = "accountTicketRedisSingle";
	public static final String APPID  = "productInventoryInfo";
	public static final String PRODUCT_INVENTORY_KEY = "/itemCache_secoo_specialProduct";
	//领取优惠券活动KEY前缀 String
	public static final String GET_TICKETS_ACTIVITY = "get_tickets_activity_";
	// 发放失败的优惠券列表 redis key
	public static final String TICKETS_ISSUE_STATUS_FAILED_LIST = "TICKETS_ISSUE_STATUS_FAILED_LIST";
	// 注册添加到发券redis中 redis key
	public static final String TICKETS_ISSUE_REGISTER_SUCESS_LIST = "TICKETS_ISSUE_REGISTER_SUCESS_LIST";
	public static final String COUPON_EXPIRATION_REMINDER_LIST = "coupon_expiration_reminder_list";
	//详情页领取优惠券列表KEY SET——维度=全部
	public static final String TICKETS_ALL_KEY = "tickets_all";
	//优惠券类型KEY前缀 String
	public static final String TICKET_TYPE_KEY = "ticket_type_";
	//详情页领取优惠券列表KEY前缀 SET——维度=分类
	public static final String TICKETS_CATEGORY_SAVE_KEY = "tickets_category_";
	//详情页领取优惠券列表KEY前缀 SET——维度=分类
	public static final String TICKETS_CATEGORY_VIEW_KEY = "tickets_category__";
	//详情页领取优惠券列表KEY前缀 SET——维度=品牌
	public static final String TICKETS_BRAND_KEY = "tickets_brand_";
	//详情页领取优惠券列表KEY前缀 SET——维度=商品
	public static final String TICKETS_PRODUCT_KEY = "tickets_product_";
	//实体、复用券KEY前缀 String
	public static final String ENTITY_TICKET = "ticket_";
	public static final String TICKET_TYPE_SN_KEY = "ticket_type_sn_";
	public static final String TICKET_USER_KEY = "ticket_user_";
	public static final String GET_TICKETS_ACTIVITY_LIMIT_KEY = "get_tickets_activity_limit_";
	public final static String INVENTORY_FREEZE_APPID = "inventoryFreeze";
	public final static String TICKET_KEY = "secoo_ticket_";
	public final static String TICKET_INFO = "secoo_ticket_info";
	public final static String AUCTION_TICKET_USER_KEY = "auction_ticket_user_";

	public static final String GET_TICKETS_ACTIVITY_LIMIT_USER_DAY_KEY = "get_tickets_activity_user_day_limit_";
	public static final String GET_TICKETS_ACTIVITY_LIMIT_USER_COUNT_KEY = "get_tickets_activity_user_count_limit_";
	public static final String GET_TICKETS_ACTIVITY_LIMIT_DAY_KEY = "get_tickets_activity_day_limit_";
	public static final String GET_TICKETS_ACTIVITY_LIMIT_COUNT_KEY = "get_tickets_activity_count_limit_";
	public static final String TICKET_VERIFY_OBTAIN_KEY = "ticket_verify_obtain_";

	// 发放失败的优惠券列表 redis key
	public static final String TICKETS_ISSUE_FAILED_LIST = "TICKETS_ISSUE_FAILED_LIST";
	// 注册添加到发券redis中 redis key
	public static final String TICKETS_ISSUE_SUCESS_LIST = "TICKETS_ISSUE_SUCESS_LIST";

	public static final int CACHE_TICKET_EXPIRE_TIME = 15 * 24 * 60 * 60;
	public static final int CACHE_TICKET_EXPIRE_TIME_ONE_HOUR = 60 * 60;
	public static final String FRONT_SERVER = "frontServer";

	//lua
	private static final String EVAL_UPDATE_TICKET_FILE = "update_ticket_batch.lua";

	// 批量扣减优惠券脚本
	public static String UPDATE_TICKET_EVAL_SCRIPT = "";

	/*static {
		try {
			UPDATE_TICKET_EVAL_SCRIPT = readToString(EVAL_UPDATE_TICKET_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	/***
	 * 读取脚本
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readToString(String fileName) throws IOException {
		String encoding = "UTF-8";
		//InputStream in = RedisUtils.class.getClassLoader().getResourceAsStream(fileName);
		File file = new File(fileName);
		InputStream in = new FileInputStream(file);
		byte[] byt = new byte[in.available()];
		in.read(byt);
		in.close();
		return new String(byt, encoding);
	}
	
}
