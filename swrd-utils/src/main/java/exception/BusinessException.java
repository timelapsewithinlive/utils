package exception;

/**
 * 业务异常
 *
 * @version 1.0.0
 */
public class BusinessException extends RuntimeException {

  /**
   * created by Peiyuxin on 2016年8月18日 下午3:16:26
   */
  private static final long serialVersionUID = 304163706169422949L;
  private final int code;
  private final String type;

  /**
   * 构建异常
   *
   * @param code    错误码,默认-1
   * @param message 异常信息,默认""
   * @param type    异常类型,默认""
   * @param cause   异常原因
   */
  public BusinessException(int code, String message, String type, Throwable cause) {
    super(message, cause);
    this.code = code;
    this.type = type;
  }

  /**
   * 构建异常
   *
   * @param code    错误码,默认-1
   * @param message 异常信息,默认""
   * @param cause   异常原因
   */
  public BusinessException(int code, String message, Throwable cause) {
    this(code, message, "", cause);
  }

  /**
   * 构建异常
   *
   * @param code    错误码,默认-1
   * @param message 异常信息,默认""
   * @param type    异常类型,默认""
   */
  public BusinessException(int code, String message, String type) {
    this(code, message, type, null);
  }

  /**
   * 构建异常
   *
   * @param code    错误码
   * @param message 异常信息
   */
  public BusinessException(int code, String message) {
    this(code, message, "", null);
  }

  /**
   * 构建异常
   *
   * @param code  错误码
   * @param cause 异常原因
   */
  public BusinessException(int code, Throwable cause) {
    this(code, "", "", cause);
  }

  /**
   * 构建异常
   *
   * @param message 异常信息,默认""
   * @param type    异常类型,默认""
   * @param cause   异常原因
   */
  public BusinessException(String message, String type, Throwable cause) {
    this(-1, message, type, cause);
  }

  /**
   * 构建异常
   *
   * @param message 异常信息,默认""
   * @param type    异常类型,默认""
   */
  public BusinessException(String message, String type) {
    this(-1, message, type, null);
  }


  /**
   * 构建异常
   *
   * @param message 异常信息
   * @param cause   异常原因
   */
  public BusinessException(String message, Throwable cause) {
    this(-1, message, "", cause);
  }

  /**
   * @param message 异常信息
   */
  public BusinessException(String message) {
    this(-1, message, "", null);
  }


  /**
   * @param code 错误码
   */
  public BusinessException(int code) {
    this(code, "", "", null);
  }

  /**
   * 获取错误码,默认-1
   *
   * @return
   */
  public int getCode() {
    return code;
  }

  /**
   * 异常类型,默认为null
   *
   * @return
   */
  public String getType() {
    return type;
  }
}
