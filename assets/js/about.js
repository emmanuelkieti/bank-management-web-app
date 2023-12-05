const aboutData = {
  about:{
    motto:"Your immediate money solution",
    coreValues:["Money safety","Professionalism","Integrity","Harwork"]
  },
  location:{
    online:"We are online, We will serve you by the pressing of a button.",
    onsite: "You can as well visit us at:<br/>Street: cde<br/>Building: abc<br/>Floor: efg<br/> House number: xy"
  },
  contacts:{
    intro: "Visit our official social media accounts to consume our content on money saving and most viable investments.",
    twitter:"abc",
    facebook: "abc",
    youtube: "abc",
    telegram: "abc"
  },
  renderData(){
    let coreValuesString = `<ul>`;
    for(let i = 0; i < aboutData.about.coreValues.length; i++)
      coreValuesString += `<li>${aboutData.about.coreValues[i]}</li>`;
    coreValuesString += "</ul>";

    return (`<div class="about">
    <p>we are OBANK, ${aboutData.about.motto}</p>
    <h3>Our core values:</h3>
    ${coreValuesString}</div><div class="location">
    <h3>Location</h3>
    <p>${aboutData.location.online}</p>
    <p>${aboutData.location.onsite}</p>
    </div><div class="contacts"><p>${aboutData.contacts.intro}</p>
    <p>Twitter: ${aboutData.contacts.twitter}<br/>Facebook: ${aboutData.contacts.facebook}<br/>
    Youtube: ${aboutData.contacts.youtube}<br/>Telegram: ${aboutData.contacts.telegram}<br/></p></div>`);
    
  }
}

body.getElementsByTagName("main")[0].innerHTML = aboutData.renderData();