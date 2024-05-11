export function groupBy(array, key) {
  return array.reduce((acc, item) => {
    (acc[item[key]] = acc[item[key]] || []).push(item)
    return acc
  }, {})
}

export function searchInArrayByKeyAndGetField(array, key, value, field) {
  const result = array.find(item => item[key] === value);
  return result ? result[field] : null;
}

export function filterInArrayByKeyAndGetField(array, key, value, field) {
    const result = array.filter(item => item[key] === value);
    return result.length ? result.map(item => item[field]).join(', ') : null;
}