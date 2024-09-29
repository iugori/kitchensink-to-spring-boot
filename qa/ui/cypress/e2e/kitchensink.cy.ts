describe('template spec', () => {
  it('passes', () => {
    // cy.visit('http://localhost:8080/kitchensink');
    cy.visit('http://localhost:8910');

    const title = "Welcome to JBoss!";
    const titleElement = cy.get('h1').first();
    titleElement.should('include.text', title);

    const johnEmailTd = cy.get('td').contains('john.smith@mailinator.com');
    johnEmailTd.should("be.visible");

    const johnUrlTd = johnEmailTd.parent().children().last();
    johnUrlTd.should("contain.text", "/rest/members/0");

    const registerBtn = cy.get("input.register");
    registerBtn.should("be.visible");

    let validMessages = cy.get("span.invalid");
    validMessages.should("have.length", 0);

    registerBtn.click();

    validMessages = cy.get("span.invalid");
    validMessages.should("have.length", 3);

  })
})