let banner = body.getElementsByClassName("banner")[0];
  const homePageData = {
    bannerInformation:{
      heading:"THE BEST BANKING SOLUTION REGIONALLY",
      services:["Money transfer and safety","Investments","Loans","Mobile banking"]
    },
    additionalInfor:["Banking at the palm of your hand.",
    "We are spread and we continue spreading.",
    "Send money accross the world.",
    "Try us today."]
  }

  banner.getElementsByClassName('banner-content')[0].innerHTML = `<p>${homePageData.bannerInformation.heading}</p>
    <h3>Our services:</h3>`;
  let servicesString = `<ul>`;
  for(let i = 0; i<homePageData.bannerInformation.services.length;i++)servicesString +=
    `<li>- ${homePageData.bannerInformation.services[i]}</li>`;
  servicesString += `</ul>`;
  banner.getElementsByClassName('banner-content')[0].innerHTML += servicesString;

  //dropping coins animation
  for(let i = 0; i < 10; i++){
    let coin = document.createElement('div');
    coin.innerHTML = "$1";
    coin.classList.add("coin");
    banner.appendChild(coin);
  }
  requestAnimationFrame(moveCoins);
  function moveCoins(time){
    if(time < 15_000) {
      const coins = banner.getElementsByClassName('coin');
      for(let i = 1; i < coins.length; i++) {
        coins[i].style.top = (((time/(50*i))%banner.clientHeight)) + 'px';
        coins[i].style.transform = `rotateX(${(time/15)%360}deg)`;
      }
      requestAnimationFrame(moveCoins);
    }
  }//end dropping coins

  let adString = `<ul>`;
  for(let i = 0; i < homePageData.additionalInfor.length; i++)
    adString += `<li>${homePageData.additionalInfor[i]}</li>`;
  adString += "</ul>";
  body.getElementsByClassName('ad')[0].innerHTML = adString;