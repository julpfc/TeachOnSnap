
$("#passwordFormButton").on('click', function () {		
	var ret = $("#pwn1")[0].checkValidity() && $("#pwn2")[0].checkValidity() && ($("#pwn1").prop('value') != $("#pwn2").prop('value'));
		
	if(ret){		
		$("#pwn2")[0].setCustomValidity($("#pwnMatch").prop('value'));
	} else {
		$("#pwn2")[0].setCustomValidity('');
	}		
})

$("#passwordForm").on('submit', function () {
	return $("#pwn1").prop('value') == $("#pwn2").prop('value');		
})
