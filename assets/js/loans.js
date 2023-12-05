const href = location.href;

function validateAmount(amount){
  if(amount.search(/[^0-9.]/) != -1 || amount == "") return false;
  return true;
}

if(body.getElementsByClassName("apply-loan").length){
  body.getElementsByClassName("apply-loan")[0].innerHTML = `
  <form action="loans" method="post">
  <h2>Apply Loan</h2>
  <div class="apply-loan-response"></div>
  <p><strong>Note1:</strong> The interest is <em>8%</em></p>
  <p><strong>Note2:</strong> You can Not borrow more than <strong>3/4</strong> of your current account balance.</p>
  <p><strong>Note3:</strong> If successful, we will deposit the loan amount into your account.</p>

  Type the amount you wish to borrow:  <input type="text" name="applyAmount" placeholder="eg 100.00"><br/>
  <button type="submit" name="submitting" value="submit">APPLY LOAN</button>
  </form>`;

  const form = document.forms[0];
  let response = body.querySelector(".apply-loan-response");

  form.addEventListener('submit', (event)=>{
    let amount = form.applyAmount.value;
    if(!validateAmount(amount)) {
      response.innerHTML = "Check your input";
      event.preventDefault();
    }
  });
  //error check after application
  if(href.includes("high-amount")) response.innerHTML = "Try lower amount.";
  if(href.includes("existing-loan")) response.innerHTML = "Request Unsuccessful. You have an existing loan";
  if(href.includes("apply-loan=1")){
    response.innerHTML = "Your loan has been approved and amount deposited into your account";
    response.style.backgroundColor = "green";
    response.style.color = "white";
  }
}

if(body.getElementsByClassName("pay-loan").length){
  body.getElementsByClassName("pay-loan")[0].innerHTML = `
  <form action="loans" method="post">
  <h2>Pay Loan</h2>
  <div class="pay-loan-response"></div>
  <p><strong>Note1:</strong> We will deduct the amount from your account</p>
  <p><strong>Note2:</strong> Amount due your loan will be reduced by the same amount.</p>

  Type the amount you wish to Pay:  <input type="text" name="payAmount" placeholder="eg 100.00"><br/>
  <button type="submit" name="submitting" value="submit">PAY LOAN</button>
  </form>`;

  const form = document.forms[0];
  let response = body.querySelector(".pay-loan-response");

  form.addEventListener('submit', (event)=>{
    let amount = form.payAmount.value;
    if(!validateAmount(amount)) {
      response.innerHTML = "Check your input";
      event.preventDefault();
    }
  });
  //error check after pay attempts
  if(href.includes("balance-low")) response.innerHTML = "Your account balance is lower than than the amount you wish to pay.";
  if(href.includes("no-loan")) response.innerHTML = "You have no existing loan";
  if(href.includes("fully-paid")){
    response.innerHTML = "Your loan is fully paid";
    response.style.backgroundColor = "green";
    response.style.color = "white";
  }
  if(href.includes("no-error")){
    response.innerHTML = "Loan amount successfully paid";
    response.style.backgroundColor = "green";
    response.style.color = "white";
  }
}

if(href.includes("no-loan")) response.innerHTML = "You have no existing loan";//for loan status