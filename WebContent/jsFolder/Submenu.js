function list(file1, file2) {
	var submenu;
	var rawFile = new XMLHttpRequest();
	rawFile.open("POST", file1, false);
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
	rawFile.open("POST", file2, false);
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
	var str='';
		for(var name in itemssub) {
		str += '<li class="nav-item"><a class="nav-link" href="' + itemssub[name] + '.jsp">' + itemsmenu[name] + '</a></li>'
	}
	document.write(str);
}
list("list", "menu");