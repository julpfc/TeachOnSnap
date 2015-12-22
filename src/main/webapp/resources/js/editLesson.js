function reloadUploadedFiles(data) {	
	if(data){
		var len = data.length;
		var uploadedFiles = $("#uploaded-files");
		uploadedFiles.empty();
		if(len==0){
			uploadedFiles.append(
	    			$('<tr/>')
	    				.append($('<td/>').text(msg['lesson.form.media.select'])
	    	));		
		}
	    $.each(data, function (index, file) {        	  
	    	uploadedFiles.append(
	    			$('<tr/>')
	    				.append($('<td/>').html("<input type='radio' name='index' value='"+index+"' required "+((index==len-1)?"checked='checked'":'')+"/>"))
	                    .append($('<td/>').text(file.fileName))
	                    .append($('<td/>').html("<span class='glyphicon glyphicon-"+((file.fileType.match('video\/.*')!=null)?"film":((file.fileType.match('audio\/.*')!=null)?"volume-up":"picture"))+"'></span>"))
	                    .append($('<td/>').html("<span class='glyphicon glyphicon-remove' onclick=$.ajax('"+appHost"/upload/?r="+index+"').done(function(data){reloadUploadedFiles(data);})></span>"))
	        );
	    	uploadedFiles.append(
	    			$('<tr/>')
	    				.append($('<td/>'))
	                    .append($('<td/>').html("<a href='"+appHost+"/upload/?f="+index+"'>"+msg['lesson.form.media.download']+"</a>"))
	                    .append($('<td/>').text((file.fileSize/1024/1024).toFixed(2)))
	                    .append($('<td/>').text("MB"))
	    	);
	    });		
	}
}

function validateFiles(data) {
	var validFiles = [];
	$("#uploadFile").empty();

    $.each(data.files, function (index, file) {
    	if(file.size > maxFileSize || file.size==0){
    		$("#uploadFile").append(
	    			$('<tr/>').append($('<td/>').text(file.name + ' ' + msg['lesson.form.media.upload.ignore.size'] + ' -> ' + (file.size/1024/1024).toFixed(2) +' MB')));            		
    	}            	
    	else {
    		var i;
    		var accepted = false;
    		for (i in acceptedFileTypes) {
    		    accepted = file.type.match(acceptedFileTypes[i]+'\/.*')!=null;
    		    if(accepted) break;
    		}
    		
    		if( !accepted){    	
        		$("#uploadFile").append(
    	    			$('<tr/>').append($('<td/>').text(file.name + ' ' + msg['lesson.form.media.upload.ignore.type'] + ' -> '+ file.type)));
        	}
    		else{
    			$("#uploadFile").append(
    					$('<tr/>').append($('<td/>').text(msg['lesson.form.media.upload.progress'] + ' ' + file.name + ' ' + file.type + ' ('+ (file.size/1024/1024).toFixed(2)+' MB)')));
    			validFiles.push(data.files[index]);
    		}
    	}

    });
    
    return validFiles;
}

function removeMedia(){
	$("#uploadDiv").removeClass('hidden');
	$("#mediaDiv").empty();
	$("#mediaDiv").html("<input type='hidden' name='remove' value='true'/>");
	$("#confirm").modal('hide');
}

$('#lessonForm').on('submit.confirm',function(e){
    e.preventDefault();
    confirmSubmit('lesson.confirm',$('#lessonForm'));   
});


$(document).ready(function() {
    
	$('[data-toggle="popover"]').popover(); 
	
	var tags = $('#tags');
	var formTags = $('#formTags');
	var newTag = $('#inputLessonTag');
	
	$('#addTag').on('click', function(event) {
		var tag = newTag.prop('value');
		
		if (tag){
			tag = tag.toLowerCase();
			tags.append('<span class="label label-default" onclick="this.remove();$(\'#tag_'+CryptoJS.MD5(tag)+'\').remove();">'+tag+'</span> ');
			formTags.append('<option id="tag_'+CryptoJS.MD5(tag)+'" selected="selected">'+tag+'</option>');
			newTag.prop('value','');
			newTag.focus();
		}
    });
	
	var sources = $('#sources');
	var formSource = $('#formSources');
	var newSource = $('#inputLessonSource');
	
	$('#addSource').on('click', function(event) {
		var source = newSource.prop('value');
		
		if (source){
			sources.append('<tr onclick="this.remove();$(\'#sources_'+CryptoJS.MD5(source)+'\').remove();"><td><span class="glyphicon glyphicon-link"></span> '+source+'</td></tr>');
			formSource.append('<option id="sources_'+CryptoJS.MD5(source)+'" selected="selected">'+source+'</option>');
			newSource.prop('value','');
			newSource.focus();
		}
    });
	
	
	var moreInfo = $('#moreInfo');
	var formMoreInfo = $('#formMoreInfo');
	var newMoreInfo = $('#inputLessonMoreInfo');
	
	$('#addMoreInfo').on('click', function(event) {
		var moreInfoURL = newMoreInfo.prop('value');
		
		if (moreInfoURL){
			moreInfo.append('<tr onclick="this.remove();$(\'#more_'+CryptoJS.MD5(moreInfoURL)+'\').remove();"><td><span class="glyphicon glyphicon-link"></span> '+moreInfoURL+'</td></tr>');
			formMoreInfo.append('<option id="more_'+CryptoJS.MD5(moreInfoURL)+'" selected="selected">'+moreInfoURL+'</option>');
			newMoreInfo.prop('value','');
			newMoreInfo.focus();
		}
    });

	
	$('#removeMedia').on('click', function(event) {
		confirm('javascript:removeMedia();','lesson.form.media.remove.confirm');
	});
	
	$.ajax("/upload/?l=1").done(function(data){reloadUploadedFiles(data);});	
	
});

$(function () {	
    $('#fileupload').fileupload({
        dataType: 'json',       
               
        done: function (e, data) {        	
        	$("#uploadFile").empty();
        	reloadUploadedFiles(data.result);
            $("#buttonSave").prop('disabled',false);
            $('#progress').addClass('hidden');
        },
 
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            var progressbar = $('#progressbar');
        	var progressbar_bw = $('#progressbar_bw');
        	
            progressbar.css('width',progress+'%');
            progressbar.prop('aria-valuenow',progress);
            progressbar_bw.empty();
            progressbar_bw.append(progress+'%');
        },
        
        change: function (e, data) {
            
            data.files = validateFiles(data);
            
            if(data.files.length>0){
            	$("#buttonSave").prop('disabled',true);
            	$('#progress').removeClass('hidden');
            }

        },

        drop: function (e, data) {
        	            
            data.files = validateFiles(data);
            
            if(data.files.length>0){
            	$("#buttonSave").prop('disabled',true);
            	$('#progress').removeClass('hidden');
            }
        },
 
        fail: function (e,data) {        	
    	    if (data.jqXHR.status == 403) {
    	    	location.reload();
    	    }
    	    else{
    	    	$("#uploadFile").empty();
    	    	$("#uploadFile").append(
    	    			$('<tr/>').append($('<td/>').text(msg['lesson.form.media.upload.error'])));
    	    }
        },
        
        dropZone: $('#dropzone')
    });
    
});


    
    
