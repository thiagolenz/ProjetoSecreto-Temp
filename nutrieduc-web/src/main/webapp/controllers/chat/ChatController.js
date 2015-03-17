define (["components/HtmlLoader",
         "scripts/socket.io",
         "components/Service",
         "modules/chat/ChatHistoryForm",
         "modules/chat/ChatMessageAppender"],
function (htmlLoader, io, service, chatHistoryForm, chatMessageAppender){
	
	function load () {
		_this.openChats = new Array();
		htmlLoader.load({
			container: "#chatContainer",
			file: "controllers/chat/templates/ChatLayout.html",
			i18n: [{}, {}]
		}, onFinishLoad);
	}

	function onFinishLoad () {
		service.get({
			url : "userProfile/chat/getUserGroupList",
			skipLoading : true,
			success : function (data) {
				for (var i in data.groupUsers) {
					var label = document.createElement ("label");
					$(label).html(i);
					$("#groups").append(label);
					var groupUsers = document.createElement("div");
					$.each(data.groupUsers[i], function (i, value){
						var button = document.createElement("button");
						$(button).html(value.name);
						$(button).attr("data-name", value.login);
						$(button).addClass("chatUserStart");
						$(groupUsers).append(button);
					});
					$("#groups").append(groupUsers);
				}
				bindEvents();
			}
		});
		AppConfig.getConfig(function (config){
			_this.socket = io.connect(config.chatUrl);
			SessionInfo.getUserContext(function (user){
				_this.socket.emit('add user', user.login);
				_this.user = user;
			});
			_this.chatNumberCount = 0;
		})
	}
	
	function bindEvents () {
		$(".chatUserStart").click(createChatEvent);
		$("#closeChat").click (function (){
			$("#chatContainer").html("");
		});
		_this.socket.on('receive', function(msg){
			var message = JSON.parse(msg);
			var openChat = findOpenChat(message);
			if (openChat == undefined)
				createChatArea(message, function (openChat){
					appendMessageChat(message, openChat);
				});
			else 
				appendMessageChat(message, openChat);
		});
	}
	
	function createChatEvent (event) {
		createChatArea({
			userA : _this.user.login,
			userB : $(event.target).data("name"),
		});
	} 
	
	function createChatArea (options, onFinish) {
		var openChat = findOpenChat(options);
		if (openChat == undefined) {
			openChat = {
					userA :options.userA,
					userB : options.userB,
					chatId : "chatItem"+ (_this.chatNumberCount++)
			};
			_this.openChats.push(openChat);
			var chatItem = document.createElement("div");
			$(chatItem).addClass("chat");
			$(".chatarea").append(chatItem);
			
			chatItem.id = openChat.chatId;
			$(chatItem).attr("data-me", options.userA);
			$(chatItem).attr("data-other", options.userB);
			
			htmlLoader.load({
				container: "#"+chatItem.id,
				file: "controllers/chat/templates/ChatItem.html",
			}, function () {
				$(chatItem).find(".closeChat").attr("data-close", openChat.chatId);
				$(chatItem).find(".linkHistory").attr("data-container", chatItem.id);
				$(chatItem).find(".messageArea").attr("data-container", chatItem.id).focus();
				$(chatItem).find(".chatName").html(options.userB);
				bindEventsChatOpen(chatItem);
				if (onFinish)
					onFinish (chatItem);
			});	
		} else {
			$(openChat).find(".messageArea").focus();
			if (onFinish)
				onFinish (openChat);
		}	
	}
	
	function findOpenChat (message) {
		for (var i = 0; i < _this.openChats.length; i++) {
			var chat = _this.openChats[i];
			if (chat.userA == message.userA && chat.userB == message.userB)
				return "#" + chat.chatId;
			if (chat.userA == message.userB && chat.userB == message.userA)
				return "#" + chat.chatId;
		}
		return undefined;
	}
	
	function bindEventsChatOpen (chatItem) {
		$(chatItem).find(".messageArea").on("keypress", function (event) {
			if (event.keyCode == 13) {
				event.preventDefault();
				sendMessage(event.target, "#" + $(event.target).data("container"));
			}
		});
		$(chatItem).find(".closeChat").click (function (){
			var chat = $(event.target).data("close");
			$("#"+chat).remove();
			for (var i = 0; i< _this.openChats.length; i++){
				var chatItem = _this.openChats[i];
				if (chatItem.chatId == chat){
					_this.openChats.remove(chatItem);
					break;
				}
			}
		});
		$(chatItem).find(".linkHistory").click(function (event){
			var container = "#"+ $(event.target).data("container");
			var options = {
				me : $(container).data("me"),
				other: $(container).data("other"),
				name: _this.user.login
			};
			chatHistoryForm.open(options);
		});
	}

	function sendMessage (target, openChatId) {
		var message = {
				sender : _this.user.login,
				userA : $(openChatId).data("me"),
				userB : $(openChatId).data("other"),
				value : $(target).val()
		};
		_this.socket.emit('send', JSON.stringify(message));
		$(target).val('');
	}

	function appendMessageChat (message, chatOpen) {
		var name = _this.user.login;
		chatMessageAppender.append (message, chatOpen, name);
		scrollAnimate(chatOpen);
	}
	
	function scrollAnimate (chatOpen) {
		var discussion = $(chatOpen).find(".discussion");
		$(discussion).animate({ scrollTop: $(discussion)[0].scrollHeight}, 1000);
	}

	var _this = {
			load : load
	};

	return _this;
});