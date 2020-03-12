/**
 * 
 */
function ajaxPage(name) {
	$(function() {
		$.ajax({
			type: 'post',
			url: name,
			dataType: 'html',
			success: function(data){
				$('article').html(data);
				history.pushState({data:data}, "Network Security Lab", name.replace(".html", ""));
			}
		})
	})
}

$.ajax({
	type: 'post',
	url:'list',
	dataType: 'text',
	success: function(data) {
		var items = data.split(',');
		var str = '';
		for(var name in items) {
			str += '<li style="cursor:pointer" onclick="ajaxPage(\'' + items[name] + '\')">' + items[name].replace(".html", "") + '</li>';
		}
		$('.submenu').html(str);
	}
})

$(window).on('popstate', function(event) {
	var data = event.originalEvent.state;
	if(data != null) {
		$('article').html(data.list);
	} else {
		history.back();
	}
})

ajaxPage('NSLab.html');