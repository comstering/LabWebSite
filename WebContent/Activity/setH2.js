function getH2(category) {
	var submenu;
	var rawFile = new XMLHttpRequest();
	rawFile.open("POST", "list", false);
	rawFile.onreadystatechange = function() {
		if(rawFile.readyState === 4) {
			if(rawFile.status === 200 || rawfile.status == 0) {
				submenu = rawFile.responseText;
			}
		}
	}
	rawFile.send(null);
	var menuname
	var rawFile = new XMLHttpRequest();
	rawFile.open("POST", "menu", false);
	rawFile.onreadystatechange = function() {
		if(rawFile.readyState === 4) {
			if(rawFile.status === 200 || rawfile.status == 0) {
				menuname = rawFile.responseText;
			}
		}
	}
	rawFile.send(null);
	var itemssub = submenu.split(',');
	var itemsmenu = menuname.split(',');
		for(var name in itemssub) {
			if(itemssub[name] == category) {
				document.write(itemsmenu[name]);
				break;
			}
		}
}