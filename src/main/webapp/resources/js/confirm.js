
function confirm(url,msgKey){
	$("#confirmOK").prop('href',url);
	$("#confirmBody").text(msg[msgKey]);
	$("#confirm").modal();
}

function confirmSubmit(msgKey, myForm){
	$("#confirmOKButton").prop("type","submit");
	myForm.off('submit.confirm');
	$("#confirmCancel").on('click',function(){
		enableConfirm(msgKey,myForm);
	});		
	$("#confirmBody").text(msg[msgKey]);
	$("#confirm").modal();
}

function enableConfirm(msgKey, myForm){
	myForm.on('submit.confirm',function(e){
	    e.preventDefault();
	    confirmSubmit(msgKey, myForm);   
	});
}