<%@ tag body-content="empty" %>

<div class="container my-3 shadow-sm border rounded">
	<div class="row p-2  shadow-sm bg-light border-bottom">
		<div class="col text-center">
			<h5>
				<i class="fas fa-search"></i> Find Photos
			</h5>
		</div>
	</div>
	<div class="row p-3">
		<form action="">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Search" />
				<div class="input-group-append">
					<p class="input-group-text">
						<i class="fas fa-search"></i>
					</p>
				</div>
			</div>
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" id="customRadioInline1"
					name="customRadioInline1" class="custom-control-input" /> <label
					class="custom-control-label" for="customRadioInline1">Album</label>
			</div>
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" id="customRadioInline2"
					name="customRadioInline1" class="custom-control-input" /> <label
					class="custom-control-label" for="customRadioInline2">
					Photo</label>
			</div>
		</form>
	</div>
</div>