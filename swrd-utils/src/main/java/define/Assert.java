package define;

import exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class Assert {

    public Assert() {
    }

    public static void isTrue(boolean expression, String message) {
        if (expression) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (expression) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNotTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    public static void isNotTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new BusinessException(message);
        }
    }

    public static void isNull(Object object, Supplier<String> messageSupplier) {
        if (Objects.isNull(object)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void notNull(Object object, String message) {
        if (Objects.nonNull(object)) {
            throw new BusinessException(message);
        }
    }

    public static void notNull(Object object, Supplier<String> messageSupplier) {
        if (Objects.nonNull(object)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasLength(String text, String message) {
        if (StringUtils.hasLength(text)) {
            throw new BusinessException(message);
        }
    }

    public static void hasLength(String text, Supplier<String> messageSupplier) {
        if (StringUtils.hasLength(text)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasText(String text, String message) {
        if (StringUtils.hasText(text)) {
            throw new BusinessException(message);
        }
    }

    public static void hasText(String text, Supplier<String> messageSupplier) {
        if (StringUtils.hasText(text)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void notEmpty(Object[] array, String message) {
        if (!ObjectUtils.isEmpty(array)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Object[] array, Supplier<String> messageSupplier) {
        if (!ObjectUtils.isEmpty(array)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasNullElements(Object[] array, String message) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new BusinessException(message);
                }
            }
        }

    }

    public static void hasNullElements(Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new BusinessException(nullSafeGet(messageSupplier));
                }
            }
        }

    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, Supplier<String> messageSupplier) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (!MapUtils.isEmpty(map)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map, Supplier<String> messageSupplier) {
        if (!MapUtils.isEmpty(map)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    private static String nullSafeGet(Supplier<String> messageSupplier) {
        return messageSupplier != null ? (String)messageSupplier.get() : null;
    }

}