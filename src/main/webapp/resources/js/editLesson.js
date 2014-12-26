function reloadUploadedFiles(data) {
	$("#uploaded-files").empty();	
	var len = data.length;
    $.each(data, function (index, file) {        	  
    	$("#uploaded-files").append(
    			$('<tr/>')
    				.append($('<td/>').html("<input type='radio' name='index' value='"+index+"' required "+((index==len-1)?"checked='checked'":'')+"/>"))
                    .append($('<td/>').text(file.fileName))
                    .append($('<td/>').text(file.fileSize))
                    .append($('<td/>').text(file.fileType))
                    .append($('<td/>').html("<a href='/upload/?f="+index+"'>Download</a>"))
                    .append($('<td/>').html("<span class='glyphicon glyphicon-remove' onclick=$.ajax('/upload/?r="+index+"').done(function(data){reloadUploadedFiles(data);})></span>"))
    	)//end $("#uploaded-files").append()
    });		
}


$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	  var newTab = e.target.attributes['aria-controls'].value; // newly activated tab
	  var inputTab = $('#inputRadioAttach_'+newTab);
	  inputTab.trigger('click');	  
	})	

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
	$.ajax("/upload/?l=1").done(function(data){reloadUploadedFiles(data);});
	
});

$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',        
               
        done: function (e, data) {
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
            $.each(data.files, function (index, file) {
            	$("#uploaded-files").empty();
            	$("#uploaded-files").append(
            			$('<ol class="list-unstyled"/>')
                         .append($('<li/>').text(file.name))
                         .append($('<li/>').text(file.type + ' ('+ file.size+' bytes)'))                         
            	)       		
            });
            $("#buttonSave").prop('disabled',true);
            $('#progress').removeClass('hidden');
        },
        drop: function (e, data) {
            $.each(data.files, function (index, file) {
            	$("#uploaded-files").empty();
            	$("#uploaded-files").append(
            			$('<ol class="list-unstyled"/>')
                         .append($('<li/>').text(file.name))
                         .append($('<li/>').text(file.type + ' ('+ file.size+' bytes)'))                         
            	)       		
            });
            $("#buttonSave").prop('disabled',true);
            $('#progress').removeClass('hidden');
        },
 
        fail: function (e,data) {
    	    if (data.errorThrown === 'abort') {
    	        alert('File Upload has been canceled');
    	    }
        },
        
        dropZone: $('#dropzone')
    });
 
});



    
    
