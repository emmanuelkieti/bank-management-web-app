const href = location.href;

function validateAccount(account) {
  if(account.search(/[^0-9A-Z]/) != -1 || account.length < 6) return false;
  return true;
}
function validateAmount(amount){
  if(amount.search(/[^0-9.]/) != -1) return false;
  return true;
}

//deposit money form
if(body.getElementsByClassName("deposit-money-form").length) {
  document.getElementsByClassName("deposit-money-form")[0].innerHTML = `<form action ="staff-operations" method="post">
<h2> Deposit Money</h2>
<div class="deposit-money-response"></div>
Account to Deposit to: <input type="text" name="accNumber" placeholder="e.g. OB1000"><br/>
Amount:<input type="text" name="amount" placeholder="e.g. 100.00"><br/>
<button type="submit" name="submitting" value="submit">DEPOSIT</button>
</form>`;

const form = document.forms[0];
let response = body.querySelector(".deposit-money-response");

form.addEventListener('submit', (event)=>{
  if(!validateMoneyDeposit()) event.preventDefault();
});

//data validation
function validateMoneyDeposit (){
  let acTo, amount, submit;
  acTo = form.accNumber.value;
  amount = form.amount.value;
  submit = form.submitting.value;
  if(acTo == "" || amount == "" || submit == "") {
    response.innerHTML = "Fill all the fields.";
    return false;
  } else {
    if(validateAccount(acTo) && validateAmount(amount)) {
      let confirmSend  = confirm(`Confirm Deposit of $${amount} to ${acTo}`);
      if(confirmSend) return true;
      else {
        response.innerHTML = "Transaction cancelled";
        return false;
      };
    } else {
      response.innerHTML = "Check your inputs.";
      return false;
    }
  }
}
  //errors after deposit attempt
  if(href.includes("account-not-found")) response.innerHTML = "Account Number does NOT exist";
}//end deposit

//withdraw money form
if(body.getElementsByClassName("withdraw-money-form").length) {
  document.getElementsByClassName("withdraw-money-form")[0].innerHTML = `<form action ="staff-operations" method="post">
<h2> Withdraw Money</h2>
<div class="withdraw-money-response"></div>
Account to Withdraw From: <input type="text" name="accNumber" placeholder="e.g. OB1000"><br/>
Amount:<input type="text" name="amount" placeholder="e.g. 100.00"><br/>
<button type="submit" name="submitting" value="submit">WITHDRAW</button>
</form>`;

const form = document.forms[0];
let response = body.querySelector(".withdraw-money-response");

form.addEventListener('submit', (event)=>{
  if(!validateMoneyWithdraw()) event.preventDefault();
});

//data validation
function validateMoneyWithdraw (){
  let acFrom, amount, submit;
  acFrom = form.accNumber.value;
  amount = form.amount.value;
  submit = form.submitting.value;
  if(acFrom == "" || amount == "" || submit == "") {
    response.innerHTML = "Fill all the fields.";
    return false;
  } else {
    if(validateAccount(acFrom) && validateAmount(amount)) {
      let confirmSend  = confirm(`Confirm withdraw of $${amount} from ${acFrom}`);
      if(confirmSend) return true;
      else {
        response.innerHTML = "Transaction cancelled";
        return false;
      };
    } else {
      response.innerHTML = "Check your inputs.";
      return false;
    }
  }
}
  //errors after withdraw attempt
  if(href.includes("account-not-found")) response.innerHTML = "Account Number does NOT exist";
  if(href.includes("insufficient-funds")) response.innerHTML = "Insufficient funds";
}//end withdraw

//Delete client form
if(body.getElementsByClassName("delete-client-form").length) {
  document.getElementsByClassName("delete-client-form")[0].innerHTML = `<form action ="staff-operations" method="post">
<h2> Delete User </h2>
<div class="delete-client-response"></div>
Client National Id: <input type="text" name="nationalId" placeholder="the Id used to register"><br/>
<button type="submit" name="submitting" value="submit">DELETE CLIENT</button>
</form>`;

const form = document.forms[0];
let response = body.querySelector(".delete-client-response");

form.addEventListener('submit', (event)=>{
  if(!validateDeleteClient()) event.preventDefault();
});

//data validation
function validateDeleteClient(){
  let id, submit;
  id = form.nationalId.value;
  submit = form.submitting.value;
  if(id == ""|| submit == "") {
    response.innerHTML = "Provide the id please.";
    return false;
  } else {
    if(validateId(id)) {
      let confirmSend  = confirm(`Confirm removal of user ${id}`);
      if(confirmSend) return true;
      else {
        response.innerHTML = "Removal cancelled";
        return false;
      };
    } else {
      response.innerHTML = "Wrong input.";
      return false;
    }
  }
}

function validateId(id){
  if(id.search(/[^0-9]/) != -1) return false;
  return true;
}

  //errors after deletion attempt
  if(href.includes("id-not-found")) response.innerHTML = "Customer Not available";
}//delete client