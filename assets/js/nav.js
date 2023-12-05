const body = document.body;
const width = body.clientWidth;

let navString = `<ul>`;
  for(let i = 0; i < navigation.nav.length; i++) {
    if(navigation.nav[i][0] == "accounts"){
      navString +=
        `<li class="dropdown-parent"><a href="${navigation.nav[i][1]}">${navigation.nav[i][0].toUpperCase()}</a>
        <div class="dropdown accounts-dropdown"><ul>`
        for(let j = 0; j < navigation.navDropdowns.accounts.length;j++) navString+=
        `<li><a href="${navigation.navDropdowns.accounts[j][1]}">${navigation.navDropdowns.accounts[j][0]}</a></li>`;
        navString += `</ul></div></li>`;
    } else if(navigation.nav[i][0] == "loans"){
      navString +=
        `<li class="dropdown-parent"><a href="${navigation.nav[i][1]}">${navigation.nav[i][0].toUpperCase()}</a>
        <div class="dropdown loans-dropdown"><ul>`
        for(let j = 0; j < navigation.navDropdowns.loans.length;j++) navString+=
        `<li><a href="${navigation.navDropdowns.loans[j][1]}">${navigation.navDropdowns.loans[j][0]}</a></li>`;
        navString += `</ul></div></li>`;
    } else if(navigation.nav[i][0] == "staff-operations"){
      navString +=
        `<li class="dropdown-parent"><a href="${navigation.nav[i][1]}">${navigation.nav[i][0].toUpperCase()}</a>
        <div class="dropdown accounts-dropdown"><ul>`
        for(let j = 0; j < navigation.navDropdowns.staffOperations.length;j++) navString+=
        `<li><a href="${navigation.navDropdowns.staffOperations[j][1]}">${navigation.navDropdowns.staffOperations[j][0]}</a></li>`;
        navString += `</ul></div></li>`;
    } else navString +=`<li><a href="${navigation.nav[i][1]}">${navigation.nav[i][0].toUpperCase()}</a></li>`;
  }
  navString += '</ul>'
  body.getElementsByTagName('nav')[0].innerHTML = navString;

  //current page link highlight
(function currentPage () {
  let links = body.getElementsByTagName('nav')[0].querySelectorAll(`ul li a`);
  let current = location.pathname;
  for (let i of links) {
    let href = i.getAttribute("href");
    if (href == current) {
      i.parentNode.style.backgroundColor = "green";
      i.style.color = "white"
    }
  }
})();