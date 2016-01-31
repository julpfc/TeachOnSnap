$('#broadcastForm').on('submit.confirm',function(e){
	e.preventDefault();
	confirmSubmit('admin.broadcast.confirm',$('#broadcastForm'));   
});


$(function () {
  $('[data-toggle="tooltip"]').tooltip()
});


$('#previewButton').on('click',function(e){
	$('#iFrameBody').removeClass("hidden");
	var $iframe = document.getElementById("iFrameRender"), iframe, iframeDoc;
  
	if ( !$iframe ) {
		iframe = document.createElement("iframe");
		iframe.id = "iFrameRender";		
		iframe.setAttribute("class","embed-responsive-item");
	  
		document.getElementById("iFrameBody").appendChild( iframe );
	} else {
		iframe = $iframe;
	}    
   
	iframeDoc = iframe.contentWindow ? 
              iframe.contentWindow : 
              iframe.contentDocument.document ? 
                iframe.contentDocument.document : 
                iframe.contentDocument;

	var body = msg['notify.html.broadcast.template'];
	body = body.replace(/\{1\}/g, appHost);
	if(document.getElementById("iFrameMessage").value.contains('<')){
		body = body.replace('{0}', document.getElementById("iFrameMessage").value);
	}
	else{
		body = body.replace('{0}', '<h3>' + document.getElementById("iFrameMessage").value + '</h3>');
	}
	iframeDoc.document.open();
	iframeDoc.document.write(body);
	iframeDoc.document.close();	   
});

