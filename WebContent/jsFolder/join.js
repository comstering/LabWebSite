$(document).ready(function () {
	$(function () {
		$("#Phone").keydown(function (event) {
			var key = event.charCode || event.keyCode || 0;
			$text = $(this); 
			if (key !== 8 && key !== 9) {
				if ($text.val().length === 3) {
					$text.val($text.val() + '-');
				}
				if ($text.val().length === 8) {
					$text.val($text.val() + '-');
				}
			}
			return (key == 8 || key == 9 || key == 46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105));
			// Key 8번 백스페이스, Key 9번 탭, Key 46번 Delete 부터 0 ~ 9까지, Key 96 ~ 105까지 넘버패트
			// JQuery 0 ~~~ 9 숫자 백스페이스, 탭, Delete 키 넘버패드외에는 입력못함
		})
	});
});
function checkPassword(obj) {
	var check = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%=+]).{10,}$/.test(obj);    //  영문, 특수문자, 숫자, 글자수 조건
	
	if(check) {    //  정규식 조건 검사
		return true;
	} else {
		return false;
	}
}
function checkPass() {
	var obj = $("#Password").val();
	if(!checkPassword(obj)) {
		$("#checkPass1").text("영어 대소문자, 숫자, 특수문자 포함");
		$("#checkPass2").text("10자리이상으로 입력하세요.");
		$("#checkPass1").css("color", "red");
		$("#checkPass2").css("color", "red");
	} else {
		$("#checkPass1").text("");
		$("#checkPass2").text("비밀번호 조건이 만족합니다.");
		$("#checkPass2").css("color", "green");
	}
}
function doubleCheck(obj) {
	var pass = $("#Password").val();
	
	if(pass == obj) {
		return true;
	} else {
		return false;
	}
}
function doubleCheckPass() {
	var checkPass = $("#checkPassword").val();
	
	if(!doubleCheck(checkPass)) {
		$("#doublePass").text("비밀번호와 다릅니다.");
		$("#doublePass").css("color", "red");
	} else {
		$("#doublePass").text("비밀번호와 동일합니다.");
		$("#doublePass").css("color", "green");
	}
}