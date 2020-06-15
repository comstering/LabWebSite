if(self.name != 'reload') {
	self.name = 'reload';
	self.location.reload(true);
} else {
	self.name = '';
}

$(document).ready(function() {
	$("#menu").load("../jsFolder/Menu.jsp")
});
$(document).ready(function() {
	$("#imfooter").load("../jsFolder/footer.jsp")
});