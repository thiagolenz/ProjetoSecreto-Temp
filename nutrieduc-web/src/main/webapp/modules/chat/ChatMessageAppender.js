define ([], function (){
	function append (message, chatOpen, name) {
		var container = document.createElement("li");
		var profile = document.createElement("div");
		$(profile).addClass("avatar");
		var img = document.createElement("img");
		img.src = "images/g1.jpg";
		$(profile).append(img);
		$(container).append(profile);

		var messages = document.createElement("li");
		$(messages).addClass("messages");
		$(messages).append(message.value);
		$(container).append(messages);
		var className = name == message.sender ? "self" : "other";
		$(container).addClass(className);
		var discussion = $(chatOpen).find(".discussion");
		$(discussion).append(container);
	}
	
	return {
		append : append
	};
});