package com.example.e2e;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class JourneySteps {

    private final Admin alice;
    private final Customer bob;

    public JourneySteps(Admin alice, Customer bob) {
        this.alice = alice;
        this.bob = bob;
    }

    @Given("Bob visits the shop")
    public void bob_visits_the_shop() {
        bob.visitsTheShop();
    }

    @Given("Bob adds a shirt to his basket")
    public void bob_adds_a_shirt_to_his_basket() {
        var shirt = alice.loadProduct(); // TODO this should live somewhere else
        bob.addItemToBasket(shirt);
    }

    @Given("Bab chooses to checkout as a guest")
    public void bab_chooses_to_checkout_as_a_guest() {
        bob.checkout();
    }

    @Given("Bob enters valid contact details")
    public void bob_enters_valid_contact_details() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("Bob receives and order confirmation")
    public void bob_receives_and_order_confirmation() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
