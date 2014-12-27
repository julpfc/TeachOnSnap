function reloadUploadedFiles(data,contentType) {		
	var len = data.length;
	var uploadedFiles = $("#uploaded-files_"+contentType);
	uploadedFiles.empty();
	if(len==0){
		uploadedFiles.append(
    			$('<tr/>')
    				.append($('<td/>').text('Selecciona un fichero de '+contentType+' o arrástralo hasta la zona marcada.')
    	));		
	}
    $.each(data, function (index, file) {        	  
    	uploadedFiles.append(
    			$('<tr/>')
    				.append($('<td/>').html("<input type='radio' name='index_"+contentType+"' value='"+index+"' required "+((index==len-1)?"checked='checked'":'')+" />"))
                    .append($('<td/>').text(file.fileName))
                    .append($('<td/>').text(file.fileSize))
                    .append($('<td/>').text(file.fileType))
                    .append($('<td/>').html("<a href='/upload/"+contentType+"?f="+index+"'>Download</a>"))
                    .append($('<td/>').html("<span class='glyphicon glyphicon-remove' onclick=$.ajax('/upload/"+contentType+"?r="+index+"').done(function(data){reloadUploadedFiles(data,'"+contentType+"');})></span>"))
    	)//end $("#uploaded-files").append()
    });		
}


$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	  var newTab = e.target.attributes['aria-controls'].value; // newly activated tab
	  var inputTab = $('#inputRadioAttach_'+newTab);
	  inputTab.trigger('click');
});	

$(document).ready(function() {
    
	var tags = $('#tags');
	var formTags = $('#formTags');
	var newTag = $('#inputLessonTag');
	
	$('#addTag').on('click', function(event) {
		var tag = newTag.prop('value');
		
		if (tag){
			tags.append('<span class="label label-default" onclick="this.remove();$(\'#opt_'+tag.replace(/\s+/g, '')+'\').remove();">'+tag+'</span> ');
			formTags.append('<option id="opt_'+tag.replace(/\s+/g, '')+'" selected="selected">'+tag+'</option>');
			newTag.prop('value','');
			newTag.focus();
		}
    });
	$.ajax("/upload/video?l=1").done(function(data){reloadUploadedFiles(data,'video');});
	$.ajax("/upload/audio?l=1").done(function(data){reloadUploadedFiles(data,'audio');});
	
});

$(function () {
    $('#fileupload_video').fileupload({
        dataType: 'json',        
               
        done: function (e, data) {
        	$("#uploadFile_video").empty();
        	reloadUploadedFiles(data.result,'video');
            $("#buttonSave").prop('disabled',false);
            $('#progress_video').addClass('hidden');
        },
 
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            var progressbar = $('#progressbar_video');
        	var progressbar_bw = $('#progressbar_bw_video');
        	
            progressbar.css('width',progress+'%');
            progressbar.prop('aria-valuenow',progress);
            progressbar_bw.empty();
            progressbar_bw.append(progress+'%');
        },
        
        change: function (e, data) {
            $.each(data.files, function (index, file) {
            	$("#uploadFile_video").empty();
            	$("#uploadFile_video").text('Uploading ' + file.name + ' ' + file.type + ' ('+ file.size+' bytes)');
            });
            $("#buttonSave").prop('disabled',true);
            $('#progress_video').removeClass('hidden');
        },
        drop: function (e, data) {
            $.each(data.files, function (index, file) {
            	$("#uploadFile_video").empty();
            	$("#uploadFile_video").text('Uploading ' + file.name + ' ' + file.type + ' ('+ file.size+' bytes)');
            });
            $("#buttonSave").prop('disabled',true);
            $('#progress_video').removeClass('hidden');
        },
 
        fail: function (e,data) {
    	    if (data.errorThrown === 'abort') {
    	        alert('File Upload has been canceled');
    	    }
        },
        
        dropZone: $('#dropzone_video')
    });
    
});

$(function () {
    $('#fileupload_audio').fileupload({
        dataType: 'json',        
               
        done: function (e, data) {
        	$("#uploadFile_audio").empty();
        	reloadUploadedFiles(data.result,'audio');
            $("#buttonSave").prop('disabled',false);
            $('#progress_audio').addClass('hidden');
        },
 
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            var progressbar = $('#progressbar_audio');
        	var progressbar_bw = $('#progressbar_bw_audio');
        	
            progressbar.css('width',progress+'%');
            progressbar.prop('aria-valuenow',progress);
            progressbar_bw.empty();
            progressbar_bw.append(progress+'%');
        },
        
        change: function (e, data) {
            $.each(data.files, function (index, file) {
            	$("#uploadFile_audio").empty();
            	$("#uploadFile_audio").text('Uploading ' + file.name + ' ' + file.type + ' ('+ file.size+' bytes)');
            });
            $("#buttonSave").prop('disabled',true);
            $('#progress_audio').removeClass('hidden');
        },
        drop: function (e, data) {
            $.each(data.files, function (index, file) {
            	$("#uploadFile_audio").empty();
            	$("#uploadFile_audio").text('Uploading ' + file.name + ' ' + file.type + ' ('+ file.size+' bytes)');
            });
            $("#buttonSave").prop('disabled',true);
            $('#progress_audio').removeClass('hidden');
        },
 
        fail: function (e,data) {
    	    if (data.errorThrown === 'abort') {
    	        alert('File Upload has been canceled');
    	    }
        },
        
        dropZone: $('#dropzone_audio')
    });
    
});

    
    
