/* 	This code is for LiveFree website. This contains 
 *	all js data.
 */

// Function to show nav bar
function openNav() {
	if(window.matchMedia("(min-width: 480px)").matches) {
		document.getElementById("SideBar").style.width = "300px"
	}
	else {
		document.getElementById("SideBar").style.width = "100%"
	}
}

// Function to hide nav bar
function closeNav() {
	document.getElementById("SideBar").style.width = "0px";
	document.body.style.backgroundColor = ""
}

// Set image to match screen size
function load(){
	var screenHeight = window.innerHeight;
	var screenWidth = window.innerWidth;
	document.getElementById('bg').width=screenWidth-13;
	document.getElementById('bg').height=screenHeight;
}