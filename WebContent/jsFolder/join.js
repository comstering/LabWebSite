function inputPhoneNumber(obj) {
    var number = obj.value.replace(/[^0-9]/g, "");
    var phone = "";
    if(number.length < 4) {
        return number;
    } else if(number.length < 7) {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3);
    } else if(number.length < 11) {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3, 3);
        phone += "-";
        phone += number.substr(6);
    } else {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3, 4);
        phone += "-";
        phone += number.substr(7);
    }
    obj.value = phone;
}	
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