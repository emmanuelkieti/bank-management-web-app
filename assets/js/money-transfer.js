body.getElementsByClassName("mny-transfer-form")[0].innerHTML = `<form action ="money-transfer" method="post">
<h2> MONEY TRANSFER</h2>
<div class="money-transfer-response"></div>
ACCOUNT TO TRANSFER TO:   <input type="text" name="acto" placeholder="eg OB1000"><br/>
YOUR ACCOUNT(ACCOUNT TO TRANSFER FROM):  <input type="text" name="acfrom" placeholder="eg OB1001"><br/>
AMOUNT:  <input type="text" name="amount" placeholder="eg 100.00"><br/>
<button type="submit" name="submitting" value="submit">TRANSFER FUNDS</button>
</form>`;

const form = document.forms[0];
let response = body.querySelector(".money-transfer-response");

form.addEventListener('submit', (event)=>{
  if(!validateMoneyTransfer()) event.preventDefault();
});

//data validation
function validateMoneyTransfer (){
  let acTo, acFrom, amount, submit;
  acTo = form.acto.value;
  acFrom = form.acfrom.value;
  amount = form.amount.value;
  submit = form.submitting.value;
  if(acTo == "" || acFrom == "" || amount == "" || submit == "") {
    response.innerHTML = "Empty fields not allowed.";
    return false;
  } else {
    if(acTo == acFrom) {
      response.innerHTML = "Same account transfer.";
      return false;
    }
    if(validateAccount(acTo) && validateAccount(acFrom) && validateAmount(amount)) {
      let confirmSend  = confirm(`Confirm transfer of $${amount} from ${acFrom} to ${acTo}`);
      if(confirmSend) return true;
      else {
        response.innerHTML = "Transaction cancelled";
        return false;
      };
    }
    else{
      response.innerHTML = "Check your inputs.";
      return false;
    }
  }
}

function validateAccount(account) {
  if(account.search(/[^0-9A-Z]/) != -1 || account.length < 6) return false;
  return true;
}
function validateAmount(amount){
  if(amount.search(/[^0-9.]/) != -1) return false;
  return true;
}

//check for errors after transfer attempt
const href = location.href;
if(href.includes("account-not-found")) response.innerHTML = "Transfer Unsuccessful. The account you're transferring to doesn't exist";
if(href.includes("not-user-account")) response.innerHTML = "Transfer Unsuccessful. You can only transfer from your account";
if(href.includes("low-funds")) response.innerHTML = "Transfer Unsuccessful. Your balance is low";