bool verifyResult(result) {
  if(result['code'] == 0 && result['data'] != null) {
    return true;
  }
  return false;
}