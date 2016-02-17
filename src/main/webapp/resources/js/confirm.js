/*
 * Initialize and shows the confirm modal
 */
function confirm(url,msgKey){
	$("#confirmOK").prop('href',url);
	$("#confirmBody").text(msg[msgKey]);
	$("#confirm").modal();
}

/*
 * Shows a confirm modal before submitting the form.
 * POST can be canceled if user wants to.
 */
function confirmSubmit(msgKey, myForm){
	$("#confirmOKButton").prop("type","submit");
	myForm.off('submit.confirm');
	$("#confirmCancel").on('click',function(){
		enableConfirm(msgKey,myForm);
	});		
	$("#confirmBody").text(msg[msgKey]);
	$("#confirm").modal();
}

/*
 * Enables submit event again if user cancels
 */
function enableConfirm(msgKey, myForm){
	myForm.on('submit.confirm',function(e){
	    e.preventDefault();
	    confirmSubmit(msgKey, myForm);   
	});
}