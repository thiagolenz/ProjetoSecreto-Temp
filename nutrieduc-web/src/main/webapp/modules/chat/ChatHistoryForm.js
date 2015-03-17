define (["components/HtmlLoader",
         "components/Service",
         "modules/chat/ChatMessageAppender"], 
function (htmlLoader, service, chatMessageAppender){
	
	function open (options){
		console.log("open ");
		console.log(options);
		var dialogContainer = document.createElement("div");
		$("body").append(dialogContainer);
		dialogContainer.id = "historyModalDialogContainer";
		htmlLoader.load({
			container: "#historyModalDialogContainer",
			file: "modules/chat/templates/ChatHistoryForm.html",
		}, function () {
			$("#historyModalDialog").modal();
			loadHistory(options);
		});
	}
	
	function loadHistory (options) {
		var request = {
			userB : options.other
		};
		service.post({
			url : "userProfile/chat/allHistory",
			data : request,
			inModal: true,
			success : function (data) {
				var chatOpen = "#historyModalDialogContainer";
				for (var i = 0 ; i < data.dataList.length; i++) {
					chatMessageAppender.append(data.dataList[i], chatOpen, options.name);
				}
			}
		});
	}
	
	return {
		open : open
	};
});