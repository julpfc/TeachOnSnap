
	var video = document.getElementById('video');
    var downloadURL = document.getElementById('download-url');
    var messages = document.getElementById('error-messages');
    var startRecording = document.getElementById('start-recording');
    var stopRecording = document.getElementById('stop-recording');
    
    var audio = document.getElementById('audio');
    var downloadURL_audio = document.getElementById('audio-url-preview');
    var messages_audio = document.getElementById('error-messages-audio');
    var startRecordingAudio = document.getElementById('start-recording-audio');
    var stopRecordingAudio = document.getElementById('stop-recording-audio');
    var audioStream;
    var recorder;

    startRecording.onclick = function() {
        startRecording.disabled = true;
        stopRecording.disabled = false;

        captureUserMedia(function(stream) {
            window.audioVideoRecorder = window.RecordRTC(stream, {
                type: 'video' // don't forget this; otherwise you'll get video/webm instead of audio/ogg
            });
            window.audioVideoRecorder.startRecording();
        });
    };

    stopRecording.onclick = function() {
        stopRecording.disabled = true;
        startRecording.disabled = false;

        window.audioVideoRecorder.stopRecording(function(url) {
            downloadURL.innerHTML = '<a href="' + url + '" download="RecordRTC.webm" target="_blank">Save RecordRTC.webm to Disk!</a>';
            video.src = url;
            video.muted = false;
            video.play();
            
            video.onended = function() {
                video.pause();
                
                // dirty workaround for: "firefox seems unable to playback"
                video.src = URL.createObjectURL(audioVideoRecorder.getBlob());
            };
        });
    };

    function captureUserMedia(callback) {
        navigator.getUserMedia = navigator.mozGetUserMedia || navigator.webkitGetUserMedia;
        navigator.getUserMedia({ audio: true, video: true }, function(stream) {
            video.src = URL.createObjectURL(stream);
            video.muted = true;
            video.controls = true;
            video.play();
            
            callback(stream);
        }, function(error) { messages.innerHTML=error; });
    }
    
    startRecordingAudio.onclick = function() {
    	if (!audioStream)
	    	navigator.getUserMedia({ audio: true, video: false }, function(stream) {
	                if (window.IsChrome) stream = new window.MediaStream(stream.getAudioTracks());
	                audioStream = stream;
	
	                audio.src = URL.createObjectURL(audioStream);
	                audio.muted = true;
	                audio.play();
	
	                // "audio" is a default type
	                recorder = window.RecordRTC(stream, {
	                    type: 'audio'
	                });
	                recorder.startRecording();
	        }, function(error) { messages_audio.innerHTML=error; });
    	 else {
             audio.src = URL.createObjectURL(audioStream);             
             audio.muted = true;	
             audio.play();
             if (recorder) recorder.startRecording();
         }
        window.isAudio = true;

        this.disabled = true;
        stopRecordingAudio.disabled = false;
    };
    
    stopRecordingAudio.onclick = function() {
        this.disabled = true;
        startRecordingAudio.disabled = false;
        audio.src = '';

        if (recorder)
            recorder.stopRecording(function(url) {
                audio.src = url;
                audio.muted = false;
                audio.play();
                
                downloadURL_audio.innerHTML = '<a href="' + url + '" target="_blank">Recorded Audio URL</a>';
            });
    };
    