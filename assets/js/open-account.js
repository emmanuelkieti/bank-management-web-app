body.getElementsByClassName("open-account-form")[0].innerHTML = `<form action ="open-account" method="post">
<h2> Open Account</h2>
<div class="open-account-response"></div>
Your National ID Number:<input type="text" name="idNumber"><br/>
First Name:<input type="text" name="fname"><br/>
Email:<input type="text" name="email"><br/>
Phone number:<input type="text" name="phone" placeholder="write ten digits only..."><br/>
Account Name:<input type="text" name="accountName"><br/>
<button type="submit" name="submit" class="submit">SUBMIT</button>
</form>`;

const form = document.forms[0];
let response = body.querySelector(".open-account-response");

form.addEventListener('submit', (event)=>{
  if(!validateAccountOpening()) event.preventDefault();
});

//data validation
function validateAccountOpening (){
  let id, fname, email, phone, accountName;
  id = form.idNumber.value;
  fname = form.fname.value;
  email = form.email.value;
  phone = form.phone.value;
  accountName = form.accountName.value;
  
  if(id.length == 0 || fname.length == 0 || email.length == 0 || phone.length == 0 || accountName.length == 0) {
    response.innerHTML = "You Must fill all the fields!!";
    return false;
  } else {
    let validFname = validateFirstAccountNames(fname, "First Name");
    let validEmail = vaidateEmail(email);
    if(!validEmail) response.innerHTML = "Invalid email format";
    
    let validAccount = validateFirstAccountNames(accountName, "Account Name");

    if(validateId(id)){
      if(validFname) {
        if(validEmail){
          if(validatePhoneNumber(phone)){
            if(validAccount)return true;
            else return false;
          } else return false;
        }else return false;
      } else return false;
    } else return false;
  }
}

function validateId(id){
  for(let i = 0; i< id.length; i++)
    if(/[^0-9]/.test(id[i])) {
      response.innerHTML = "Wrong ID format. ID must be numbers only.";
      return false;
    }
  return true;
}

function validateFirstAccountNames(parameter, name){
  let flag = false;
  for(let i = 0; i < parameter.length; i++)
    if(/[^0-9]/gi.test(parameter[i])) {
      flag = true;
      break;
    }
    if(flag == false){
      response.innerHTML = name+" cannot be digits alone.";
      return false;
    }
    return true;
}

function vaidateEmail(email){
  let dotFlag = false;
  let atFlag = false;
  for(let i = 0; i < email.length; i++) {
    if(email[i] == '@') {
      for(let j = 0; j < email.length; j++)
        if(email[j] == '.' && j > i) dotFlag = true;
      
      if(i-1 != undefined && i+1 != undefined) atFlag = true;
    }
  }
  if(dotFlag && atFlag) return true;
  return false;
}

function validatePhoneNumber (phone) {
  if(phone.length != 10) {
    response.innerHTML = "Phone number should have 10 digits";
    return false;
  } else {
    for(let i = 0; i < phone.length; i++)
      if(/[^0-9]/.test(phone[i])) {
        response.innerHTML = `Phone number can only contain numbers`;
        return false;
      }
  }
  return true;
}