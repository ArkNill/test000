/* 원본 : https://beta.observablehq.com/@mbostock/d3-pie-chart 
   참고 : https://falsy.me/d3-js-%eb%a5%bc-%ec%82%ac%ec%9a%a9%ed%95%98%ec%97%ac-%eb%8d%b0%ec%9d%b4%ed%84%b0-%ec%8b%9c%ea%b0%81%ed%99%94%ed%95%98%ea%b8%b0-4-custom-bar-charts/ */

const barWidth1 = 400;
const barHeight1 = 400;
const barMargin1 = {top: 40, left: 40, bottom: 40, right: 40};
 
const barData1 = [
    {name: 'A', value: 10, color: '#5487b1'},
    {name: 'B', value: 29, color: '#63a1af'},
    {name: 'C', value: 32, color: '#7ab8aa'},
    {name: 'D', value: 25, color: '#93caa8'},
    {name: 'E', value: 23, color: '#add7a8'},
    {name: 'F', value: 15, color: '#c6e3a7'}
  ];
 
const barX1 = d3.scaleBand()
  .domain(barData1.map(d => d.name))
  .range([barMargin1.left, barWidth1 - barMargin1.right])
  .padding(0.4);
 
const barY1 = d3.scaleLinear()
  .domain([0, d3.max(barData1, d => d.value)]).nice()
    .range([barHeight1 - barMargin1.bottom, barMargin1.top]);
 
const barXAxis = g => g
  .attr('transform', `translate(0, ${barHeight1 - barMargin1.bottom})`)
  .call(d3.axisBottom(barX1)
    .tickSizeOuter(0))
  .call(g => g.select('.domain').remove())
  // x축의 기준선을 지웁니다.
  .call(g => g.selectAll('line').remove());
  // x축의 구분선을 지웁니다.
 
// line chart와 동일
const barYAxis = g => g
  .attr('transform', `translate(${barMargin1.left}, 0)`)
  .call(d3.axisLeft(barY1))
  .call(g => g.select('.domain').remove())
  // y축의 기준선을 지웁니다.
  .call(g => g.selectAll('line')
    .attr('x2', barWidth1)
    .style('stroke', '#f5f5f5'))
  // y축의 구분선의 너비 값을 그래프의 너비값으로 설정하고 구분선의 색을 #f5f5f5 로 합니다.
  // svg의 가로 선은 x 속성의 값의 출발점으로 x2의 값을 끝점으로 선을 그립니다.
 
const barSvg1 = d3.select('#bar-svg-area1').append('svg').style('width', barWidth1).style('height', barHeight1);
// id 값이 bar-svg-area1인 엘리먼트를 찾고 그래프를 그릴 svg엘리먼트를 추가합니다.
 
barSvg1.append('g').call(barXAxis);
barSvg1.append('g').call(barYAxis);
barSvg1.append('g')
  .selectAll('rect').data(barData1).enter().append('rect')
  .attr('x', d => barX1(d.name))
  .attr('y', d => barY1(d.value))
  .attr('height', d => barY1(0) - barY1(d.value))
  .attr("rx", 15)
  // svg rect 엘리먼트의 rx 속성으로 둥군 모서리를 설정할 수 있습니다.
  .attr('width', barX1.bandwidth())
  .attr('fill', d => d.color)
  .attr('data-x', d => d.name)
  // 해당 데이터의 이름값을 데이터셋 x 값으로 추가합니다.
  .attr('data-y', d => d.value)
  // 해당 데이터의 벨류값을 데이터셋 y 값으로 추가합니다.
  .attr('data-color', d=> d.color);
  // 해당 데이터의 컬러값을 데이터셋 color 값으로 추가합니다.
 
barSvg1.node();
 
const rectEl = document.getElementsByTagName('rect');
// 현재 도큐멘트의 rect 엘리먼트들을 담습니다.
const tooltop = document.getElementById('bar-tooltip1');
// 미리 html에 만들어 놓은 id값 bar-tooltip1의 엘리먼트를 담습니다.
 
for(const el of rectEl) {
  // 도큐멘트의 rect 엘리먼트들에게 마우스 오버 이벤트를 추가합니다.
  el.addEventListener('mouseover', (event) => {
    // 해당 rect 엘리먼트
    const target = event.target;
    // 툴팁이 위치할 x축의 값입니다.
    // svg 엘리먼트는 offset 값을 가지고 있지 않아서, 해당 엘리먼트의 데이터셋으로 추가한 x 값과 막대의 넓이 그리고 툴팁의 너비값을 이용해 정의합니다.
    const positionLeft = Number(target.getAttribute('x')) + Number(barX1.bandwidth()/2) - tooltop.clientWidth/2;
    // 툴팁이 위치한 y축의 값입니다.
    // 역시 offset 값이 없기 때문에 막대의 높이값과 툴팁의 높이값을 이용해 정의합니다.
    // 마지막의 5는 막대와 툴팁의 여백값입니다.
    const positionTop = barHeight1 - barMargin1.top - target.getAttribute('height') - tooltop.clientHeight - 5;
    // 아까 추가한 rect 데이터셋의 컬러값을 가져옵니다.
    const color = target.dataset.color;
    // 아까 추가한 rect 데이터셋의 벨류값을 가져옵니다.
    const value = target.dataset.y;
 
    // 벨류값을 text로 넣습니다.
    tooltop.innerText = value;
    // 툴팁의 배경색을 막대 색과 같게 합니다.
    tooltop.style.background = color;
    // 툴팁의 위치를 지정합니다.
    tooltop.style.top = positionTop + 'px';
    tooltop.style.left = positionLeft + 'px';
    // 투명하게 했던 툴팁을 보이게 합니다.
    tooltop.style.opacity = 1;
  });
}