body.getElementsByClassName("login-form")[0].innerHTML = `<form action ="login" method="post">
<h2> LOGIN</h2>
<div class="login-response"></div>
ACCOUNT NUMBER:  <input type="text" name="accNumber"><br/>
Email:<input type="text" name="email"><br/>
<button type="submit" name="submit" class="submit" value="submit">LOG IN</button>
</form>`;

const form = document.forms[0];
let response = body.querySelector(".login-response");

form.addEventListener('submit', (event)=>{
  if(!validateLogin()) event.preventDefault();
});

function validateLogin(){
  let accNumber = form.accNumber.value, email = form.email.value;
  if(accNumber == "" || email == "") {
    response.innerHTML = "You Must fill all the fields!!";
    return false;
  }
  return true;
}
//check for incorrect logins
if(location.href.includes("incorrect")) response.innerHTML = "Check Logins!!";