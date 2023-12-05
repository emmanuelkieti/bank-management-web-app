public class Header {
  private String title;
  private String styles;

  Header(String title, String styles) {
    this.title = title;
    this.styles = styles;
  }

  String headString(){
    return("<!DOCTYPE html><html lang='en-us'><head><meta charset='utf-8' /><meta name='viewport' content='width=device-width, initial-scale=1' /><meta name='theme-color' content='#ffffff' />" +
    "<meta name='description' content='Your banking solutions' /><meta name='keywords' content='obank, banks around, banking, best banking solutions' /><link rel='icon' href='./assets/images/logo.png'/>"+
    "<link rel='stylesheet' href="+"'"+styles+"' />"+ "<title>"+title+"</title></head><body>");
  }

  String headerString () {
    //No logout link
    return("<div class='top-bar'>call us: +254 717 676 644</div><header>"+
    "<div class='logo'><img src='./assets/images/logo.png' width='50' height='50' style='border: 1px solid black;' alt='logo'/>"+
    "<p> OUR BANK<br><span>Your immediate money solution</span></p></div><div class='user'><img src='./assets/images/usericon.png' alt='user icon'/>"+
    "<ul><li>- you're NOT logged in</li><li><a href='/obank/login'>Log in</a></li></ul></div></header>");
  }
  String sessionHeader(){
    //No login link
    return("<div class='top-bar'>call us: +254 717 676 644</div><header>"+
    "<div class='logo'><img src='./assets/images/logo.png' width='50' height='50' style='border: 1px solid black;' alt='logo'/>"+
    "<p> OUR BANK<br><span>Your immediate money solution</span></p></div><div class='user'><img src='./assets/images/usericon.png' alt='user icon'/>"+
    "<ul><li>- you're logged in</li><li><a href='/obank/logout'>Log out</a></li></ul></div></header>");
  }

  String navString(){
    return("<script>const navigation = {nav:[['home','/obank/home'], ['about','/obank/about'], ['accounts','#']],navDropdowns:{accounts:[['Open account', '/obank/open-account']]}}</script>");
  }

  String sessionNav(){
    String ns = "<script>const navigation = {"+
    "nav:[['home','/obank/home'], ['about','/obank/about'], ['accounts','#'], ['loans','#']],"+
    "navDropdowns:{accounts:[['My Balance', '/obank/check-balance'], ['Money Transfer', '/obank/money-transfer']],"+
    "loans:[['Apply loan', '/obank/loans?apply-loan=true'], ['Pay loan', '/obank/loans?pay-loan=true'], ['Loan Status', '/obank/loans?loan-status=true']]}}</script>";
    return ns;
  }
  String staffNav(){
    String ns = "<script>const navigation = {nav:[['staff-operations','#']],"+
    "navDropdowns:{staffOperations:[['Deposit', '/obank/staff-operations?deposit=1'], ['Withdraw', '/obank/staff-operations?withdraw=1'],"+
    "['All clients', '/obank/staff-operations?allClients=1'], ['All accounts', '/obank/staff-operations?allAccounts=1'], ['All loans', '/obank/staff-operations?allLoans=1'],"+
    "['All staff', '/obank/staff-operations?allStaff=1'], ['DELETE Client', '/obank/staff-operations?delete-client=1']]"+
    "}}</script>";
    return ns;
  }

  String renderNavString() {
    return ("<nav></nav><script src='./assets/js/nav.js'></script>");
  }
}