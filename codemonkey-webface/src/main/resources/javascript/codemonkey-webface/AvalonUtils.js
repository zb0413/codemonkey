var AvalonUtils = {
	heredoc : function (fn) {
	    return fn.toString().replace(/^[^\/]+\/\*!?\s?/, '').replace(/\*\/[^\/]+$/, '').trim().replace(/>\s*</g, '><');
	}
};

