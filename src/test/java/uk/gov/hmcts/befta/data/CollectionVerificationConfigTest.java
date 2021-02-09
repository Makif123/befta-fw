/**
 * 
 */
package uk.gov.hmcts.befta.data;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.befta.data.CollectionVerificationConfig.Operator;
import uk.gov.hmcts.befta.data.CollectionVerificationConfig.Ordering;
import uk.gov.hmcts.befta.exception.InvalidTestDataException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableCollection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author korneleehenry
 *
 */
class CollectionVerificationConfigTest {

	private static final String CASE_ID = "case_id";
	private static final String CASE_ID_VALUE = "case_id_value";
	private static final String USER_ID = "user_id";
	private static final String CASE_ROLE_KEY = "case_role_key";
	private static final String USER_ID_VALUE = "user_id_value";
	private static final String CASE_ROLE_VALUE = "case_role_value";

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#equals(java.lang.Object)}.
	 */
	@Test
	void testEqualsObject() {
		CollectionVerificationConfig actual = new CollectionVerificationConfig();
		CollectionVerificationConfig other = new CollectionVerificationConfig(Operator.EQUIVALENT, Ordering.ORDERED, "Id2");
		assertEquals(CollectionVerificationConfig.DEFAULT,actual);
		assertEquals(CollectionVerificationConfig.DEFAULT.hashCode(),actual.hashCode());
		assertNotEquals(actual.hashCode(),other.hashCode());
		assertNotEquals(actual,other);
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#toString()}.
	 */
	@Test
	void testToString() {
		CollectionVerificationConfig actual = new CollectionVerificationConfig();
		CollectionVerificationConfig other = new CollectionVerificationConfig(Operator.EQUIVALENT, Ordering.ORDERED, "Id2");
		assertNotNull(actual.toString());
		assertNotNull(other.toString());
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigFromNullCollection() {
		assertEquals(CollectionVerificationConfig.DEFAULT, CollectionVerificationConfig.getVerificationConfigFrom(null));
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigFromEmptyCollection() {
		assertEquals(CollectionVerificationConfig.DEFAULT,
				CollectionVerificationConfig.getVerificationConfigFrom(unmodifiableCollection(Collections.emptyList())));
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigReturnsDefaultFromListCollectionWhereirstElementIsCollectionVerificationConfig() {
		assertEquals(CollectionVerificationConfig.DEFAULT,
				CollectionVerificationConfig.getVerificationConfigFrom(unmodifiableCollection(asList(CollectionVerificationConfig.DEFAULT))));
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigReturnsDefaultFromListCollectionWhereFirstElementIsNotCollectionVerificationConfig() {

		assertEquals(CollectionVerificationConfig.DEFAULT,
				CollectionVerificationConfig.getVerificationConfigFrom((Collection<?>) Collections.EMPTY_LIST));
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigReturnsDefaultFromMapNotContainingDoubleUnderscoredKeys() {

		List<Map<String, String>> listOfMaps = new ArrayList<>();
		listOfMaps.add(Collections.emptyMap());

		assertEquals(CollectionVerificationConfig.DEFAULT,
				CollectionVerificationConfig.getVerificationConfigFrom(listOfMaps));
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigReturnsConfigFromMapContainingOperatorFieldName() {

		Map<String, String> map = new HashMap<>();

		map.put(CollectionVerificationConfig.OPERATOR_FIELD_NAME, Operator.SUBSET.name());

		List<Map<String, String>> listOfMaps = new ArrayList<>();
		listOfMaps.add(map);

		CollectionVerificationConfig collectionVerificationConfig = new CollectionVerificationConfig(Operator.SUBSET, Ordering.ORDERED, "id");

		assertEquals(collectionVerificationConfig,
				CollectionVerificationConfig.getVerificationConfigFrom(listOfMaps));
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigReturnsConfigFromMapContainingOperatorFieldNameAndOrderingFieldNameORDERED() {

		Map<String, String> map = new HashMap<>();

		map.put(CollectionVerificationConfig.OPERATOR_FIELD_NAME, Operator.SUBSET.name());
		map.put(CollectionVerificationConfig.ORDERING_FIELD_NAME, Ordering.ORDERED.name());

		List<Map<String, String>> listOfMaps = new ArrayList<>();
		listOfMaps.add(map);

		CollectionVerificationConfig collectionVerificationConfig = new CollectionVerificationConfig(Operator.SUBSET, Ordering.ORDERED, "id");

		assertEquals(collectionVerificationConfig,
				CollectionVerificationConfig.getVerificationConfigFrom(listOfMaps));
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigReturnsConfigFromMapContainingOperatorFieldNameAndOrderingFieldNameUNORDERED() {

		Map<String, String> map = new HashMap<>();

		map.put(CollectionVerificationConfig.OPERATOR_FIELD_NAME, Operator.SUBSET.name());
		map.put(CollectionVerificationConfig.ORDERING_FIELD_NAME, Ordering.UNORDERED.name());
		map.put(CollectionVerificationConfig.ELEMENT_ID_FIELD_NAME, "idFieldName");
		List<Map<String, String>> listOfMaps = new ArrayList<>();
		listOfMaps.add(map);

		CollectionVerificationConfig collectionVerificationConfig = new CollectionVerificationConfig(Operator.SUBSET, Ordering.UNORDERED, "idFieldName");

		assertEquals(collectionVerificationConfig,
				CollectionVerificationConfig.getVerificationConfigFrom(listOfMaps));
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigReturnsConfigFromMapContainingOperatorFieldNameAndOrderingFieldNameUNORDEREDAutoCalculatedFieldNames() {

		Map<String, String> metaDataMap = new HashMap<>();

		metaDataMap.put(CollectionVerificationConfig.OPERATOR_FIELD_NAME, Operator.SUBSET.name());
		metaDataMap.put(CollectionVerificationConfig.ORDERING_FIELD_NAME, Ordering.UNORDERED.name());

		Map<String, String> dataElement1 = new HashMap<>();
		dataElement1.put(CASE_ID, CASE_ID_VALUE);
		dataElement1.put(USER_ID, USER_ID_VALUE);
		dataElement1.put(CASE_ROLE_KEY, CASE_ROLE_VALUE);

		Map<String, String> dataElement2 = new HashMap<>();
		dataElement2.put(CASE_ID, CASE_ID_VALUE + "_1");
		dataElement2.put(USER_ID, USER_ID + "_1");
		dataElement2.put(CASE_ROLE_KEY, CASE_ROLE_VALUE + "_1");


		List<Map<String, String>> listOfMaps = new ArrayList<>();
		listOfMaps.add(metaDataMap);
		listOfMaps.add(dataElement1);
		listOfMaps.add(dataElement2);

		CollectionVerificationConfig collectionVerificationConfig = new CollectionVerificationConfig(Operator.SUBSET, Ordering.UNORDERED, "case_id,case_role_key,user_id");

		assertEquals(collectionVerificationConfig,
				CollectionVerificationConfig.getVerificationConfigFrom(listOfMaps));
	}

	/**
	 * Test method for {@link uk.gov.hmcts.befta.data.CollectionVerificationConfig#getVerificationConfigFrom(Collection)} ()}.
	 */
	@Test
	void testVerificationConfigThrowsExceptionWhenCannotCalculateFieldNames() {

		Map<String, String> metaDataMap = new HashMap<>();

		metaDataMap.put(CollectionVerificationConfig.OPERATOR_FIELD_NAME, Operator.SUBSET.name());
		metaDataMap.put(CollectionVerificationConfig.ORDERING_FIELD_NAME, Ordering.UNORDERED.name());

		Map<String, String> dataElement1 = new HashMap<>();
		dataElement1.put(CASE_ID, CASE_ID_VALUE);

		Map<String, String> dataElement2 = new HashMap<>();
		dataElement2.put(CASE_ID, CASE_ID_VALUE);

		List<Map<String, String>> listOfMaps = new ArrayList<>();

		listOfMaps.add(metaDataMap);
		listOfMaps.add(dataElement1);
		listOfMaps.add(dataElement2);

		assertThrows(InvalidTestDataException.class, () -> CollectionVerificationConfig.getVerificationConfigFrom(listOfMaps));
	}
}
