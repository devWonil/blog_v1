let index = {
	
	init: function() {
		$("#btn-save").bind("click", () => {
			alert("btn-save 버튼이 눌러졌습니다");
		});
	},
	
	save: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
	}
	
}

index.init();