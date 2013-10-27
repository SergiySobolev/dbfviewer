$(document).ready(
		function() {
			$(".edit").click(function() {
				var currentId = $(this).attr('id');
				currentRowId = currentId.substr(4);
				var valueClass = "." + "table_value" + currentRowId;
				var edt_valClass = "." + "edit_input" + currentRowId;
				$(valueClass).hide();
				$(edt_valClass).show();
				$("#edit" + currentRowId).hide();
				$("#confirm" + currentRowId).show();
				$('#cncl' + currentRowId).show();
			});
			$(".cncl").click(function() {
				var currentId = $(this).attr('id');
				currentRowId = currentId.substr(4);
				var valueClass = "." + "table_value" + currentRowId;
				var edt_valClass = "." + "edit_input" + currentRowId;
				$(valueClass).show();
				$(edt_valClass).hide();
				$("#edit" + currentRowId).show();
				$("#confirm" + currentRowId).hide();
				$("#cncl" + currentRowId).hide();
			});
			
			$(".confirm").click(function() {
				var currentId = $(this).attr('id');
				var currentRowId = currentId.substr(7);
				$("#row_id").val(currentRowId);
			});
			
			$("#encodingSelect").change(function() {
				var currEncoding = $("#encodingSelect").val();
				$("#encodingId").val(currEncoding);
				$("#indexForm").submit();
			});

			$(".signSliding").hide();
			$(".show_hide").show();
			$('.show_hide').click(function() {
				$(".signSliding").slideToggle();
			});

			$("#addFilter").click(
					function() {							
							var lastRowId	= 		$('#filterTable >tbody >tr').length-1;								
						//	var selectId    = 		'filterField'.concat(lastRowId);						
						//	var inputId		=		'filterValue'.concat(lastRowId);
						//	var deleteId	=		'delete'.concat(lastRowId);
							var selectId	=		'filterField';
							var inputId		=		'filterValue';
							var deleteId	=		'delete';
							var newRowId	=		lastRowId + 1;						
							var newSelectId =		'filterField'.concat(newRowId);
							var newValueId	=		'filterValue'.concat(newRowId);		
							var newDeleteId = 		'delete'.concat(newRowId);
						//	var newSelect	=		$('#'.concat(selectId)).clone(true).attr('id', newSelectId);
						//	var newInput	=		$('#'.concat(inputId)).clone(true).attr('id', newValueId);							
						//	var newImage    =       $('#'.concat(deleteId)).clone(true).attr('id', newDeleteId);
							var newSelect	=		$('#'.concat(selectId)).clone(true);
							var newInput	=		$('#'.concat(inputId)).clone(true);							
							var newImage    =       $('#'.concat(deleteId)).clone(true);
							
							$("#filterTable").find('tbody')
							.append($('<tr>')
									.append($('<td>').append(newSelect))
									.append($('<td>').append(newInput).append(newImage)));	
							if($('#filterTable >tbody >tr').length > 1 && ($("#removeFilter").attr('disabled')=='disabled')){
								$("#removeFilter").prop('disabled', false);
							}
							
				});
			
			$("#removeFilter").click(function(){
				var tblToRmv = $('#filterTable');				
				if($('#filterTable >tbody >tr').length > 1){					
					$('tbody tr:last', tblToRmv).remove();
				}					
				if($('#filterTable >tbody >tr').length < 2){						
					$("#removeFilter").prop('disabled', true);
				}
			});
			
			$("#filterTable .delete").click(function(){	
				var tableLength 	= 		$('#filterTable >tbody >tr').length;				
				if(tableLength > 1){
					var rowId 		= 		$(this).closest('tr').index();				
					var inputId		=		'filterValue'.concat(rowId);
					$(this).closest('tr').animate({ opacity: 'hide' }, "slow");
					$('#'.concat(inputId)).val("");		
					$(this).closest('tr').remove();
					tableLength 	= 		$('#filterTable >tbody >tr').length;
					if(tableLength <= 1){
						$("#removeFilter").prop('disabled', true);
					}
				}
			});			
});