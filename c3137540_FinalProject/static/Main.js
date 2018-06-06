/*Responsive design for phone/mobile devices
 * Adds navigation link element to left side of page when screen is too small for the larger sized navigation links*/
function responsiveLeftNav() 
{
    var x = document.getElementById("leftNavBar");
	
    if (x.className === "leftnav") {
    	x.className += " responsive";
    } else {
    	x.className = "leftnav";
	}
}