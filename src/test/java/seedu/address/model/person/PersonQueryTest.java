package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

public class PersonQueryTest {

    @Test
    public void filter_empty_expectTrue() {
        assertTrue(PersonQuery.build().filter(ALICE));
    }

    @Test
    public void filter_byName() {
        PersonQuery query = PersonQuery
                .build()
                .setName(new String[]{"Pauline", "Doe"});
        assertTrue(query.filter(ALICE));
        assertFalse(query.filter(BOB));
    }

    @Test
    public void filter_byPhone() {
        PersonQuery query = PersonQuery
            .build()
            .setPhone(ALICE.getPhone());
        assertTrue(query.filter(ALICE));
        assertFalse(query.filter(BOB));
    }

    @Test
    public void filter_byAddress() {
        PersonQuery query = PersonQuery
            .build()
            .setAddress(ALICE.getAddress());
        assertTrue(query.filter(ALICE));
        assertFalse(query.filter(BOB));
    }

    @Test
    public void filter_byEmail() {
        PersonQuery query = PersonQuery
            .build()
            .setEmail(ALICE.getEmail());
        assertTrue(query.filter(ALICE));
        assertFalse(query.filter(BOB));
    }

    @Test
    public void filter_bySingleTag() {
        PersonQuery query = PersonQuery
            .build()
            .setTags(ALICE.getTags());
        // ALICE tag (friends) is a subset of BENSON tag (owesMoney, friends)
        assertTrue(query.filter(ALICE));
        assertTrue(query.filter(BENSON));
    }

    @Test
    public void filter_byMultipleTags() {
        PersonQuery query = PersonQuery
            .build()
            .setTags(BENSON.getTags());
        // ALICE tag (friends) is a subset of BENSON tag (owesMoney, friends)
        assertFalse(query.filter(ALICE));
        assertTrue(query.filter(BENSON));
    }

    @Test
    public void filter_byRank() {
        PersonQuery query = PersonQuery
            .build()
            .setRank(BOB.getRank());
        assertTrue(query.filter(ALICE));
        assertTrue(query.filter(BOB));
        assertFalse(query.filter(AMY));
    }

    @Test
    public void filter_chain_expectTrue() {
        PersonQuery query = PersonQuery
            .build()
            .setRank(ALICE.getRank())
            .setPhone(ALICE.getPhone());
        assertTrue(query.filter(ALICE));
        assertFalse(query.filter(BOB));
    }

    @Test
    public void filter_chain_expectFalse() {
        PersonQuery query = PersonQuery
            .build()
            .setName(new Name[]{ALICE.getName()})
            .setPhone(BOB.getPhone());
        assertFalse(query.filter(ALICE));
        assertFalse(query.filter(BOB));
    }

    @Test
    public void equals() {
        PersonQuery aliceQuery = PersonQuery.build();
        PersonQuery bobQuery = PersonQuery.build();

        // comparing empty queries -> returns true
        assertTrue(aliceQuery.equals(bobQuery));

        // same values -> returns true
        aliceQuery.setAddress(ALICE.getAddress());
        bobQuery.setAddress(ALICE.getAddress());
        assertTrue(aliceQuery.equals(bobQuery));

        // same object -> returns true
        assertTrue(bobQuery.equals(bobQuery));

        // null -> returns false
        assertFalse(aliceQuery.equals(null));

        // different type -> returns false
        assertFalse(aliceQuery.equals(5));

        // different name -> returns false
        aliceQuery = PersonQuery.build().setName(new Name[]{ALICE.getName()});
        bobQuery = PersonQuery.build().setName(new Name[]{BOB.getName()});
        assertFalse(aliceQuery.equals(bobQuery));

        // different phone -> returns false
        aliceQuery = PersonQuery.build().setPhone(ALICE.getPhone());
        bobQuery = PersonQuery.build().setPhone(BOB.getPhone());
        assertFalse(aliceQuery.equals(bobQuery));

        // different email -> returns false
        aliceQuery = PersonQuery.build().setEmail(ALICE.getEmail());
        bobQuery = PersonQuery.build().setEmail(BOB.getEmail());
        assertFalse(aliceQuery.equals(bobQuery));

        // different address -> returns false
        aliceQuery = PersonQuery.build().setAddress(ALICE.getAddress());
        bobQuery = PersonQuery.build().setAddress(BOB.getAddress());
        assertFalse(aliceQuery.equals(bobQuery));

        // different tags -> returns false
        aliceQuery = PersonQuery.build().setTags(ALICE.getTags());
        bobQuery = PersonQuery.build().setTags(BOB.getTags());
        assertFalse(aliceQuery.equals(bobQuery));
    }
}
