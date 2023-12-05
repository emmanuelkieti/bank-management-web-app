body.getElementsByClassName("staff-login-form")[0].innerHTML = `<form action ="staff-login" method="post">
<h2> LOGIN</h2>
<div class="staff-login-response"></div>
Staff-ID:  <input type="text" name="staffid"><br/>
Passcode:  <input type="text" name="passcode"><br/>
<button type="submit" name="submitting" value="submit">LOG IN</button>
</form>`;

const form = document.forms[0];
let response = body.querySelector(".staff-login-response");

form.addEventListener('submit', (event)=>{
  if(!validateLogin()) event.preventDefault();
});
function validateLogin(){
  let id = form.staffid.value;
  let passcode = form.passcode.value;
  if(id == "" || passcode == "") {
    response.innerHTML = "Fill all the fields";
    return false;
  }
  if(!validateId(id)) return false;
  return true;
}

function validateId(id){
  if(id.search(/[^0-9]/) != -1) {
    response.innerHTML = "Staff Id is numbers only";
    return false;
  }
  return true;
}

//Response after login attempt
if(location.href.includes("incorrect")) response.innerHTML = "Incorrect Logins!!";