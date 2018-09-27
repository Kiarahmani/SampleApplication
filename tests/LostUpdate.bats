#!/usr/bin/env zunit
# 
@setup {
  # Load a script to set up the test environment
  load test_scripts.sh
}

@test '(1) Executing 4 concurrent inc txns' {
  run lu
  assert "$output" equals "(1,100)"
}
@test '(2) Executing 4 concurrent inc txns' {
  run lu
  assert "$output" equals "(1,100)"
}
@test '(3) Executing 4 concurrent inc txns' {
  run lu
  assert "$output" equals "(1,100)"
}
@test '(4) Executing 4 concurrent inc txns' {
  run lu
  assert "$output" equals "(1,100)"
}
@test '(5) Executing 4 concurrent inc txns' {
  run lu
  assert "$output" equals "(1,100)"
}
@test '(6) Executing 4 concurrent inc txns' {
  run lu
  assert "$output" equals "(1,100)"
}
@test '(7) Executing 4 concurrent inc txns' {
  run lu
  assert "$output" equals "(1,100)"
}
@test '(8) Executing 4 concurrent inc txns' {
  run lu
  assert "$output" equals "(1,100)"
}
