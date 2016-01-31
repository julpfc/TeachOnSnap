function showEditName(show){	
	var span = $('#span-name');
	var div = $('#div-name');
	
	if(show){
		showEditPassword(false);
		showEditType(false);
		showEditStatus(false);
		showGroups(false);
		showEditExtra(false);
		showEditAvatar(false);
		span.addClass("hidden");
		div.removeClass("hidden");
	}
	else{
		span.removeClass("hidden");
		div.addClass("hidden");
	}
	
}

function showEditPassword(show){
	var span = $('#span-password');
	var div = $('#div-password');
	
	if(show){
		showEditName(false);
		showEditType(false);
		showEditStatus(false);
		showGroups(false);
		showEditExtra(false);
		showEditAvatar(false);
		span.addClass("hidden");
		div.removeClass("hidden");
	}
	else{
		span.removeClass("hidden");
		div.addClass("hidden");
	}	
}

function showEditType(show){
	var span = $('#span-type');
	var div = $('#div-type');
	
	if(show){
		showEditName(false);
		showEditPassword(false);
		showEditStatus(false);
		showGroups(false);
		showEditExtra(false);
		showEditAvatar(false);
		span.addClass("hidden");
		div.removeClass("hidden");
	}
	else{
		span.removeClass("hidden");
		div.addClass("hidden");
	}	
}

function showEditStatus(show){	
	var span = $('#span-status');
	var div = $('#div-status');
	
	if(show){
		showEditPassword(false);
		showEditType(false);
		showEditStatus(false);
		showGroups(false);
		showEditExtra(false);
		showEditAvatar(false);
		span.addClass("hidden");
		div.removeClass("hidden");
	}
	else{
		span.removeClass("hidden");
		div.addClass("hidden");
	}	
}

function showGroups(show){	
	var span = $('#span-groups');
	var div = $('#div-groups');
	
	if(show){
		showEditPassword(false);
		showEditType(false);
		showEditStatus(false);
		showEditName(false)
		showEditExtra(false);
		showEditAvatar(false);
		span.addClass("hidden");
		div.removeClass("hidden");
	}
	else{
		span.removeClass("hidden");
		div.addClass("hidden");
	}
	
}

function showEditExtra(show){	
	var span = $('#span-extra');
	var div = $('#div-extra');
	
	if(show){
		showEditName(false);
		showEditPassword(false);
		showEditType(false);
		showEditStatus(false);
		showGroups(false);
		showEditAvatar(false);
		span.addClass("hidden");
		div.removeClass("hidden");
	}
	else{
		span.removeClass("hidden");
		div.addClass("hidden");
	}
}

function showEditAvatar(show){	
	var span = $('#span-avatar');
	var div = $('#div-avatar');
	
	if(show){
		showEditName(false);
		showEditPassword(false);
		showEditType(false);
		showEditStatus(false);
		showGroups(false);
		showEditExtra(false);
		span.addClass("hidden");
		div.removeClass("hidden");
	}
	else{
		span.removeClass("hidden");
		div.addClass("hidden");
	}
	
}


$("#passwordFormButton").on('click', function () {		
	var ret = (($("#pwo")[0] == undefined)?true:$("#pwo")[0].checkValidity()) &&  $("#pwn1")[0].checkValidity() && $("#pwn2")[0].checkValidity() && ($("#pwn1").prop('value') != $("#pwn2").prop('value'));
		
	if(ret){		
		$("#pwn2")[0].setCustomValidity($("#pwnMatch").prop('value'));
	} else {
		$("#pwn2")[0].setCustomValidity('');
	}		
})

$("#passwordForm").on('submit', function () {
	return $("#pwn1").prop('value') == $("#pwn2").prop('value');		
})
