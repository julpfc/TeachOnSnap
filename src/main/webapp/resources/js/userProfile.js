/* 
 * Toggles to show/hide edit form
 * for the user's name.
 * Hides all other forms.
 */
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

/* 
 * Toggles to show/hide edit form
 * for the user's password.
 * Hides all other forms. 
 */
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

/* 
 * Toggles to show/hide edit form
 * for the user's type.
 * Hides all other forms. 
 */
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

/* 
 * Toggles to show/hide edit form
 * for the user's status.
 * Hides all other forms. 
 */
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

/* 
 * Toggles to show/hide edit form
 * for the user's groups.
 * Hides all other forms. 
 */
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

/* 
 * Toggles to show/hide edit form
 * for the user's extra information.
 * Hides all other forms. 
 */
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

/* 
 * Toggles to show/hide edit form
 * for the user's avatar.
 * Hides all other forms. 
 */
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

/*
 * Validate passwords, old and new, on user's click.
 * Show custom message.
 */
$("#passwordFormButton").on('click', function () {		
	var ret = (($("#pwo")[0] == undefined)?true:$("#pwo")[0].checkValidity()) &&  $("#pwn1")[0].checkValidity() && $("#pwn2")[0].checkValidity() && ($("#pwn1").prop('value') != $("#pwn2").prop('value'));
		
	if(ret){		
		$("#pwn2")[0].setCustomValidity($("#pwnMatch").prop('value'));
	} else {
		$("#pwn2")[0].setCustomValidity('');
	}		
})

/*
 * Validate old and new matching passwords on user's submit
 */
$("#passwordForm").on('submit', function () {
	return $("#pwn1").prop('value') == $("#pwn2").prop('value');		
})
