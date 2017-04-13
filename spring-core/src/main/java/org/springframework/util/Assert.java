/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 验证参数的断言工具类
 * Assertion utility class that assists in validating arguments.
 *
 * <p>Useful for identifying programmer errors early and clearly at runtime.
 *
 * <p>For example, if the contract of a public method states it does not
 * allow {@code null} arguments, {@code Assert} can be used to validate that
 * contract. Doing this clearly indicates a contract violation when it
 * occurs and protects the class's invariants.
 *
 * <p>Typically used to validate method arguments rather than configuration
 * properties, to check for cases that are usually programmer errors rather
 * than configuration errors. In contrast to configuration initialization
 * code, there is usually no point in falling back to defaults in such methods.
 *
 * <p>This class is similar to JUnit's assertion library. If an argument value is
 * deemed invalid, an {@link IllegalArgumentException} is thrown (typically).
 * For example:
 *
 * <pre class="code">
 * Assert.notNull(clazz, "The class must not be null");
 * Assert.isTrue(i > 0, "The value must be greater than zero");</pre>
 *
 * <p>Mainly for internal use within the framework; consider
 * <a href="http://commons.apache.org/proper/commons-lang/">Apache's Commons Lang</a>
 * for a more comprehensive suite of {@code String} utilities.
 *
 * @author Keith Donald
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Colin Sampaleanu
 * @author Rob Harrop
 * @since 1.1.2
 */
public abstract class Assert {

	/**
	 * 断言一个boolean表达式
	 * Assert a boolean expression, throwing an {@code IllegalStateException}
	 * if the expression evaluates to {@code false}.
	 * <p>Call {@link #isTrue} if you wish to throw an {@code IllegalArgumentException}
	 * on an assertion failure.
	 * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
	 * @param expression a boolean expression
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalStateException if {@code expression} is {@code false}
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	/**
	 * 断言一个boolean表达式,jdk1.8 lamda表达式
	 * Assert a boolean expression, throwing an {@code IllegalStateException}
	 * if the expression evaluates to {@code false}.
	 * <p>Call {@link #isTrue} if you wish to throw an {@code IllegalArgumentException}
	 * on an assertion failure.
	 * <pre class="code">
	 * Assert.state(id == null,
	 *     () -&gt; "ID for " + entity.getName() + " must not already be initialized");
	 * </pre>
	 * @param expression a boolean expression
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @throws IllegalStateException if {@code expression} is {@code false}
	 * @since 5.0
	 */
	public static void state(boolean expression, Supplier<String> messageSupplier) {
		if (!expression) {
			throw new IllegalStateException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #state(boolean, String)}
	 */
	@Deprecated
	public static void state(boolean expression) {
		state(expression, "[Assertion failed] - this state invariant must be true");
	}

	/**
	 * 断言一个boolean表达式,抛出参数异常
	 * Assert a boolean expression, throwing an {@code IllegalArgumentException}
	 * if the expression evaluates to {@code false}.
	 * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
	 * @param expression a boolean expression
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if {@code expression} is {@code false}
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言一个boolean表达式,抛出参数非法异常 jdk1.8 lamda表达式
	 * Assert a boolean expression, throwing an {@code IllegalArgumentException}
	 * if the expression evaluates to {@code false}.
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, () -&gt; "The value '" + i + "' must be greater than zero");
	 * </pre>
	 * @param expression a boolean expression
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if {@code expression} is {@code false}
	 * @since 5.0
	 */
	public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
		if (!expression) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #isTrue(boolean, String)}
	 */
	@Deprecated
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	/**
	 * 断言object对象是否为null
	 * Assert that an object is {@code null}.
	 * <pre class="code">Assert.isNull(value, "The value must be null");</pre>
	 * @param object the object to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object is not {@code null}
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言object对象是否为null
	 * Assert that an object is {@code null}.
	 * <pre class="code">
	 * Assert.isNull(value, () -&gt; "The value '" + value + "' must be null");
	 * </pre>
	 * @param object the object to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the object is not {@code null}
	 * @since 5.0
	 */
	public static void isNull(Object object, Supplier<String> messageSupplier) {
		if (object != null) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #isNull(Object, String)}
	 */
	@Deprecated
	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	/**
	 * 断言object对象不为空,抛出参数非法异常
	 * Assert that an object is not {@code null}.
	 * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
	 * @param object the object to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object is {@code null}
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言object对象不为空,抛出参数非法异常 jdk1.8
	 * Assert that an object is not {@code null}.
	 * <pre class="code">
	 * Assert.notNull(clazz, () -&gt; "The class '" + clazz.getName() + "' must not be null");
	 * </pre>
	 * @param object the object to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the object is {@code null}
	 * @since 5.0
	 */
	public static void notNull(Object object, Supplier<String> messageSupplier) {
		if (object == null) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #notNull(Object, String)}
	 */
	@Deprecated
	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	/**
	 * 断言text不为空
	 * Assert that the given String is not empty; that is,
	 * it must not be {@code null} and not the empty String.
	 * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
	 * @param text the String to check
	 * @param message the exception message to use if the assertion fails
	 * @see StringUtils#hasLength
	 * @throws IllegalArgumentException if the text is empty
	 */
	public static void hasLength(String text, String message) {
		if (!StringUtils.hasLength(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 *  断言text不为空
	 * Assert that the given String is not empty; that is,
	 * it must not be {@code null} and not the empty String.
	 * <pre class="code">
	 * Assert.hasLength(name, () -&gt; "Name for account '" + account.getId() + "' must not be empty");
	 * </pre>
	 * @param text the String to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @see StringUtils#hasLength
	 * @throws IllegalArgumentException if the text is empty
	 * @since 5.0
	 */
	public static void hasLength(String text, Supplier<String> messageSupplier) {
		if (!StringUtils.hasLength(text)) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #hasLength(String, String)}
	 */
	@Deprecated
	public static void hasLength(String text) {
		hasLength(text,
				"[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	/**
	 * 断言text字符是否包含有效的内容
	 * Assert that the given String contains valid text content; that is, it must not
	 * be {@code null} and must contain at least one non-whitespace character.
	 * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
	 * @param text the String to check
	 * @param message the exception message to use if the assertion fails
	 * @see StringUtils#hasText
	 * @throws IllegalArgumentException if the text does not contain valid text content
	 */
	public static void hasText(String text, String message) {
		if (!StringUtils.hasText(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言text字符是否包含有效的内容
	 * Assert that the given String contains valid text content; that is, it must not
	 * be {@code null} and must contain at least one non-whitespace character.
	 * <pre class="code">
	 * Assert.hasText(name, () -&gt; "Name for account '" + account.getId() + "' must not be empty");
	 * </pre>
	 * @param text the String to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @see StringUtils#hasText
	 * @throws IllegalArgumentException if the text does not contain valid text content
	 * @since 5.0
	 */
	public static void hasText(String text, Supplier<String> messageSupplier) {
		if (!StringUtils.hasText(text)) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #hasText(String, String)}
	 */
	@Deprecated
	public static void hasText(String text) {
		hasText(text,
				"[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}

	/**
	 * 断言textToSearch字符串不能包含substring字符串
	 * Assert that the given text does not contain the given substring.
	 * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the text contains the substring
	 */
	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
				textToSearch.contains(substring)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言textToSearch字符串不能包含substring字符串 jdk1.8
	 * Assert that the given text does not contain the given substring.
	 * <pre class="code">
	 * Assert.doesNotContain(name, forbidden, () -&gt; "Name must not contain '" + forbidden + "'");
	 * </pre>
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the text contains the substring
	 * @since 5.0
	 */
	public static void doesNotContain(String textToSearch, String substring, Supplier<String> messageSupplier) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
				textToSearch.contains(substring)) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #doesNotContain(String, String, String)}
	 */
	@Deprecated
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,
				() -> "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
	}

	/**
	 * 断言array数组不能为空
	 * Assert that an array contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object array is {@code null} or contains no elements
	 */
	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtils.isEmpty(array)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言array数组不能为空
	 * Assert that an array contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">
	 * Assert.notEmpty(array, () -&gt; "The " + arrayType + " array must contain elements");
	 * </pre>
	 * @param array the array to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the object array is {@code null} or contains no elements
	 * @since 5.0
	 */
	public static void notEmpty(Object[] array, Supplier<String> messageSupplier) {
		if (ObjectUtils.isEmpty(array)) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #notEmpty(Object[], String)}
	 */
	@Deprecated
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * 断言array数组不能包含空元素
	 * Assert that an array contains no {@code null} elements.
	 * <p>Note: Does not complain if the array is empty!
	 * <pre class="code">Assert.noNullElements(array, "The array must contain non-null elements");</pre>
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object array contains a {@code null} element
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}

	/**
	 * 断言array数组不能包含空元素
	 * Assert that an array contains no {@code null} elements.
	 * <p>Note: Does not complain if the array is empty!
	 * <pre class="code">
	 * Assert.noNullElements(array, () -&gt; "The " + arrayType + " array must contain non-null elements");
	 * </pre>
	 * @param array the array to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the object array contains a {@code null} element
	 * @since 5.0
	 */
	public static void noNullElements(Object[] array, Supplier<String> messageSupplier) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new IllegalArgumentException(nullSafeGet(messageSupplier));
				}
			}
		}
	}

	/**
	 * 断言array数组不能包含空元素
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #noNullElements(Object[], String)}
	 */
	@Deprecated
	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
	}

	/**
	 * 断言collection集合不能为空
	 * Assert that a collection contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">Assert.notEmpty(collection, "Collection must contain elements");</pre>
	 * @param collection the collection to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the collection is {@code null} or
	 * contains no elements
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言collection集合不能为空
	 * Assert that a collection contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">
	 * Assert.notEmpty(collection, () -&gt; "The " + collectionType + " collection must contain elements");
	 * </pre>
	 * @param collection the collection to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the collection is {@code null} or
	 * contains no elements
	 * @since 5.0
	 */
	public static void notEmpty(Collection<?> collection, Supplier<String> messageSupplier) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #notEmpty(Collection, String)}
	 */
	@Deprecated
	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * 断言map不能为空
	 * Assert that a Map contains entries; that is, it must not be {@code null}
	 * and must contain at least one entry.
	 * <pre class="code">Assert.notEmpty(map, "Map must contain entries");</pre>
	 * @param map the map to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the map is {@code null} or contains no entries
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if (CollectionUtils.isEmpty(map)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言map不能为空
	 * Assert that a Map contains entries; that is, it must not be {@code null}
	 * and must contain at least one entry.
	 * <pre class="code">
	 * Assert.notEmpty(map, () -&gt; "The " + mapType + " map must contain entries");
	 * </pre>
	 * @param map the map to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the map is {@code null} or contains no entries
	 * @since 5.0
	 */
	public static void notEmpty(Map<?, ?> map, Supplier<String> messageSupplier) {
		if (CollectionUtils.isEmpty(map)) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	/**
	 * 已过时
	 * @deprecated as of 4.3.7, in favor of {@link #notEmpty(Map, String)}
	 */
	@Deprecated
	public static void notEmpty(Map<?, ?> map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	/**
	 * 断言object对象必须是type类型
	 * Assert that the provided object is an instance of the provided class.
	 * <pre class="code">Assert.instanceOf(Foo.class, foo, "Foo expected");</pre>
	 * @param type the type to check against
	 * @param obj the object to check
	 * @param message a message which will be prepended to provide further context.
	 * If it is empty or ends in ":" or ";" or "," or ".", a full exception message
	 * will be appended. If it ends in a space, the name of the offending object's
	 * type will be appended. In any other case, a ":" with a space and the name
	 * of the offending object's type will be appended.
	 * @throws IllegalArgumentException if the object is not an instance of type
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			instanceCheckFailed(type, obj, message);
		}
	}

	/**
	 *  断言object对象必须是type类型
	 * Assert that the provided object is an instance of the provided class.
	 * <pre class="code">
	 * Assert.instanceOf(Foo.class, foo, () -&gt; "Processing " + Foo.class.getSimpleName() + ":");
	 * </pre>
	 * @param type the type to check against
	 * @param obj the object to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails. See {@link #isInstanceOf(Class, Object, String)} for details.
	 * @throws IllegalArgumentException if the object is not an instance of type
	 * @since 5.0
	 */
	public static void isInstanceOf(Class<?> type, Object obj, Supplier<String> messageSupplier) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
		}
	}

	/**
	 *  断言object对象必须是type类型
	 * Assert that the provided object is an instance of the provided class.
	 * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
	 * @param type the type to check against
	 * @param obj the object to check
	 * @throws IllegalArgumentException if the object is not an instance of type
	 */
	public static void isInstanceOf(Class<?> type, Object obj) {
		isInstanceOf(type, obj, "");
	}

	/**
	 * isAssignableFrom   是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口。
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass, "Number expected");</pre>
	 * @param superType the super type to check against
	 * @param subType the sub type to check
	 * @param message a message which will be prepended to provide further context.
	 * If it is empty or ends in ":" or ";" or "," or ".", a full exception message
	 * will be appended. If it ends in a space, the name of the offending sub type
	 * will be appended. In any other case, a ":" with a space and the name of the
	 * offending sub type will be appended.
	 * @throws IllegalArgumentException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Super type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			assignableCheckFailed(superType, subType, message);
		}
	}

	/**
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 * <pre class="code">
	 * Assert.isAssignable(Number.class, myClass, () -&gt; "Processing " + myAttributeName + ":");
	 * </pre>
	 * @param superType the super type to check against
	 * @param subType the sub type to check
	 * @param messageSupplier a supplier for the exception message to use if the
	 * assertion fails. See {@link #isAssignable(Class, Class, String)} for details.
	 * @throws IllegalArgumentException if the classes are not assignable
	 * @since 5.0
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType, Supplier<String> messageSupplier) {
		notNull(superType, "Super type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
		}
	}

	/**
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
	 * @param superType the super type to check
	 * @param subType the sub type to check
	 * @throws IllegalArgumentException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}


	private static void instanceCheckFailed(Class<?> type, Object obj, String msg) {
		String className = (obj != null ? obj.getClass().getName() : "null");
		String result = "";
		boolean defaultMessage = true;
		if (StringUtils.hasLength(msg)) {
			if (endsWithSeparator(msg)) {
				result = msg + " ";
			}
			else {
				result = messageWithTypeName(msg, className);
				defaultMessage = false;
			}
		}
		if (defaultMessage) {
			result = result + ("Object of class [" + className + "] must be an instance of " + type);
		}
		throw new IllegalArgumentException(result);
	}

	private static void assignableCheckFailed(Class<?> superType, Class<?> subType, String msg) {
		String result = "";
		boolean defaultMessage = true;
		if (StringUtils.hasLength(msg)) {
			if (endsWithSeparator(msg)) {
				result = msg + " ";
			}
			else {
				result = messageWithTypeName(msg, subType);
				defaultMessage = false;
			}
		}
		if (defaultMessage) {
			result = result + (subType + " is not assignable to " + superType);
		}
		throw new IllegalArgumentException(result);
	}

	private static boolean endsWithSeparator(String msg) {
		return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
	}

	private static String messageWithTypeName(String msg, Object typeName) {
		return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
	}

	private static String nullSafeGet(Supplier<String> messageSupplier) {
		return (messageSupplier != null ? messageSupplier.get() : null);
	}

}
