/**
 * When the logout link is clicked on any page, this method is called to ensure the user really does want to click.
 * If true is returned the user is redirected to the login page.
 */

function logout(){
  if (confirm('Are you sure you want to logout?')){
    return true;
  }else{
    return false;
  }
}