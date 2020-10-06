function toggle(element, id) {
  console.log(`setting value: ${element.value} to(->) #${id}`);
  $(`#${id}`).val(element.value);
  console.log($(`#${id}`).val() == element.value? 'value successfully changed' : "value successfully didn't change");
  element.title = `выбрано: ${element.value}`;
  console.log('added to element pop-up tip');
  element.value = element.value % 5 + 1;
  console.log(`element.value changed to ${element.value}`);
}

function handle_coordinates(ctx, width, height, real_radius, max_radius) {
  console.log('handle horizontal part');
  handle_horizontal(ctx, width, height, max_radius);
  
  console.log('handle vertical part');
  handle_vertical(ctx, width, height, max_radius);
  
  console.log('handle digital signs');
  handle_signs(ctx, width, height, real_radius, max_radius);
}

function handle_horizontal(ctx, width, height, max_radius) {
  console.log('draw horizontal line');
  handle_hor_line(ctx, width / 2, width);
  
  console.log('draw direction of horizontal line');
  handle_hor_arrow(ctx, width, height / 2, width / (8 * max_radius), width / (8 * max_radius));
  
  console.log('sign horizontal line');
  handle_sign_hor_line(ctx, 'X', width, height / 2, width / (8 * max_radius));
}

function handle_vertical(ctx, width, height, max_radius) {
  console.log('draw vertical line');
  handle_vert_line(ctx, width / 2, height);
  
  console.log('draw direction of vertical line');
  handle_vert_arrow(ctx, width / 2, 0, width / (8 * max_radius), width / (8 * max_radius));
  
  console.log('sign vertical line');
  handle_sign_vert_line(ctx, 'Y', width / 2, 0, width / (8 * max_radius));
}

function handle_hor_line(ctx, y, width) {
  console.log(`drawing horline on height: ${y} and with width: ${width}`);
  ctx.fillRect(0, y, width, 1);
}

function handle_vert_line(ctx, x, height) {
  console.log(`drawing vertical line on length: ${x} and with height: ${height}`);
  ctx.fillRect(x, 0, 1, height);
}

function handle_hor_arrow(ctx, x, y, width, length) {
  console.log(`horizontal arrow at (${x}, ${y}) with width: ${width}/ length: ${length}`);
  ctx.beginPath();
  ctx.moveTo(x - length, y - width / 2);
	ctx.lineTo(x, y); // edge of arrow
	ctx.lineTo(x - length, y + width / 2);
	ctx.stroke();
}

function handle_vert_arrow(ctx, x, y, width, length) {
  console.log(`vertical arrow at (${x}, ${y}) with width: ${width}/ length: ${length}`);
  ctx.beginPath();
  ctx.moveTo(x - width / 2, y + length);
	ctx.lineTo(x, y); // edge of arrow
	ctx.lineTo(x + width / 2, y + length);
	ctx.stroke();
}

function handle_sign_hor_line(ctx, sign, x, y, offset) {
  console.log(`writing sign for horizontal elements that offset down to ${offset}`);
  ctx.fillText(sign, x - offset, y + 1.75 * offset);
}

function handle_sign_vert_line(ctx, sign, x, y, offset) {
  console.log(`writing sign for vertical elements that offset right to ${offset}`);
  ctx.fillText(sign, x + offset, y + offset);
}

function handle_signs(ctx, width, height, real_radius, max_radius) {
  for (i = -max_radius * 2; i <= max_radius * 2; ++i) {
    let digit = i / 2;
    let x = -real_radius + width / 2 + (max_radius - digit) * real_radius / max_radius;
    let y = real_radius + height / 2 - (max_radius - digit) * real_radius / max_radius;
    ctx.fillRect(x, height / 2 - max_radius / 2, 1, max_radius);
    handle_sign_hor_line(ctx, -digit, x + width / 40, height / 2 - width / 40, width / 30);
    
    ctx.fillRect(width / 2 - max_radius / 2, y, max_radius, 1);
    if (digit !== 0)
      handle_sign_vert_line(ctx, -digit, width / 2 - height / 60, y - height / 40, height / 30);
  }
}

function handle_rectangle(ctx, x, y, width, height) {
  console.log(`drawing rectangle at (${x}, ${y}) with width/height: ${width}/${height}`);
  ctx.fillRect(x, y, width, height);
}

function handle_triangle(ctx, x, y, width, height) {
  console.log(`drawing triangle with vertecex: (${x}, ${y}), (${x + width}, ${y + height}), (${x}, ${y + height})`);
  ctx.beginPath();
	ctx.moveTo(x, y);
	ctx.lineTo(x + width, y + height);
	ctx.lineTo(x, y + height);
	ctx.closePath();
	ctx.stroke();
	ctx.fill();
}

function handle_quadrant(ctx, x, y, radius) {
  console.log(`drawing 4 quadrant at (${x}, ${y})`);
	ctx.beginPath();
	// drawing arc using anticlockwise drawing
	// direction and radius at the center of canvas
	// but function uses clockwise reference direction!!! (WHY)
	ctx.arc(x, y, radius, Math.PI, Math.PI / 2, true);
	// draw another line to complete quadrant
	ctx.lineTo(x, y);
	ctx.closePath();
	ctx.stroke();
	ctx.fill();
}

function handle_drawing(radius, max_radius) {
  console.log('take the drawing area');
  let canvas = document.getElementById('area');
  console.log('creating 2d context');
  let context = canvas.getContext('2d');
  
  console.log('getting main parameters: width/height');
  let ctx_width = canvas.width;
  let ctx_height = canvas.height;
  
  console.log('calculating real radius');
  let ctx_radius = ctx_width * part;
  let real_ctx_radius = radius * ctx_radius / max_radius;
  
  context.clearRect(0, 0, ctx_width, ctx_height);
  
  console.log('set font for canvas');
  const fontName = "Sans-serif";
	const textSize = ctx_width / 60;
	context.font = `${textSize}px ${fontName}`;
  
  console.log('set brush with colours');
  context.strokeStyle = '#3399FF';
  context.fillStyle = '#3399FF';
  
  console.log('starting draw main area');
  handle_rectangle(context, ctx_width / 2 - real_ctx_radius, ctx_height / 2 - real_ctx_radius / 2, real_ctx_radius, real_ctx_radius / 2);
  handle_triangle(context, ctx_width / 2, ctx_height / 2 - real_ctx_radius / 2, real_ctx_radius / 2, real_ctx_radius / 2);
  handle_quadrant(context, ctx_width / 2, ctx_height / 2, real_ctx_radius / 2);
  
  console.log('set brush with colours');
  context.strokeStyle = '#000000';
  context.fillStyle = '#000000';
  
  console.log('area been drawn');
  console.log('starting draw auxilary elements');
  handle_coordinates(context, ctx_width, ctx_height, ctx_radius, max_radius);
}

function session_id() {
  let sess = document.getElementById("submit");
  return sess.name;
}

function buildRequest(x, y, r, key_word) {
  let ses_id = session_id();
  return `?x=${x}&y=${y}&r=${r}&${ses_id}=${key_word}`;
}

function handle_canvas_click(ctx_x, ctx_y) {
  let hitRParameter = document.getElementById('radius-changing');
  if (!hitRParameter.value) {
    alert('Нельзя определить координаты точки: не выбран радиус области');
    return;
  }
  let r = hitRParameter.value;
  let x = transformCoordinate(ctx_x, canvas.width, max_radius, part); // max_radius * (ctx_x - canvas.width / 2) / (canvas.width * part);
  let y = transformCoordinate(ctx_y, canvas.height, max_radius, part); // max_radius * (ctx_y - canvas.height / 2) / (canvas.height * part);
  console.log('all parameters set so send request');
  let newURL = window.location.href.substring(0, window.location.href.length - 1);
  window.location.href = newURL + buildRequest(x, y, r, 'check');
}

function getCursorPosition(canvas, event) {
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    handle_canvas_click(x, y);
}

function transformCoordinate(realCoordinate, fieldSize, length, proportion) {
  return length * (realCoordinate - fieldSize / 2) / (fieldSize * proportion);
}

function retransformCoordinate(imagCoordinate, fieldSize, length, proportion) {
  return fieldSize * (imagCoordinate * proportion / length + 0.5);
}


function makeDotFromRow(row) {
  let dived_x = row.cells[2];
  let dived_y = row.cells[3];
  let dived_r = row.cells[4];
  let dived_hit = row.cells[5];
  console.log(dived_x);
  let x = dived_x.textContent;
  let y = dived_y.textContent;
  let r = dived_r.textContent;
  let hit = dived_hit.textContent === "да"? true : false;
  return {"x": x, "y": y, "r": r, "hit": hit};
}

function getLastDots(table_id, last) {
  let result = [];
  let table = document.getElementById(table_id);
  let rows = table.rows;
  if (last > rows.length)
    last = rows.length - 1;
  for (i = rows.length - last; i < rows.length; ++i)
    result.push(makeDotFromRow(rows[i]));
  return result;
}

function putLastDotsOnCanvas(canvas, dots) {
  let ctx = canvas.getContext('2d');
  let width = canvas.width;
  let height = canvas.height;
  dots.forEach((dot)=>{
    let x = +dot.x;
    let y = +dot.y;
    if (dot.hit)
      ctx.fillStyle = "#000000";
    else ctx.fillStyle = "#cd0000";
    ctx.beginPath();
    let realx = retransformCoordinate(x, width, max_radius, part);
    let realy = retransformCoordinate(-y, height, max_radius, part);
    console.log(`put dot (${realx}, ${realy}) on canvas`);
    ctx.arc(realx, realy, max_radius, 0, 2*Math.PI, true);
    ctx.fill();
    ctx.stroke();
    ctx.closePath();
  });
}

const tailn = 4;
const part = 0.45;
const max_radius = 5;
const canvas = document.getElementById('area');
canvas.addEventListener('mousedown', function(e) {
    getCursorPosition(canvas, e);
});

window.onload = function() {
  handle_drawing(0, 5);
  let previous_dots = getLastDots('resulting-table', tailn);
  if (previous_dots.length !== 0) {
    let lastDot = previous_dots[previous_dots.length - 1];
    let curRadius = +lastDot.r;
    handle_drawing(curRadius, 5);
    putLastDotsOnCanvas(canvas, previous_dots);
    let radiusInput = document.getElementById('radius-changing');
    radiusInput.value = curRadius;
  }
};