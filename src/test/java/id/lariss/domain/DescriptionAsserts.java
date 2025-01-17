package id.lariss.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DescriptionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDescriptionAllPropertiesEquals(Description expected, Description actual) {
        assertDescriptionAutoGeneratedPropertiesEquals(expected, actual);
        assertDescriptionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDescriptionAllUpdatablePropertiesEquals(Description expected, Description actual) {
        assertDescriptionUpdatableFieldsEquals(expected, actual);
        assertDescriptionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDescriptionAutoGeneratedPropertiesEquals(Description expected, Description actual) {
        assertThat(expected)
            .as("Verify Description auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDescriptionUpdatableFieldsEquals(Description expected, Description actual) {
        assertThat(expected)
            .as("Verify Description relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getValue()).as("check value").isEqualTo(actual.getValue()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDescriptionUpdatableRelationshipsEquals(Description expected, Description actual) {
        // empty method
    }
}
