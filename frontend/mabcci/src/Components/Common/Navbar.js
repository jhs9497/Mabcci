import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import './Nav.css';
import { AiOutlineLogin, AiOutlineHome } from 'react-icons/ai';
import { FiUser } from 'react-icons/fi';
import { FaRegComments } from 'react-icons/fa';
import { BsImages } from 'react-icons/bs';
import { MdAccountCircle, MdAccessAlarm } from 'react-icons/md';
import { IoShirtOutline } from 'react-icons/io5';
import { Link } from 'react-router-dom';
import logo from '../../Asset/Images/logo.png';

function Nav() {
  // 로그인 여부 redux까지 연결 되어야함
  const [isLoggedin, setisLoggedin] = useState(false);
  useEffect(() => {
    const localLoinToken = localStorage.getItem('accessToken');
    if (localLoinToken) {
      setisLoggedin(true);
    } else {
      setisLoggedin(false);
    }
  });

  const loginRedux = useSelector(state => state.LoginReducer);

  // popover 띄우기
  const [popover, setpopover] = useState(false);

  // mypage icon제외 클릭시 popover 사라지게 하기
  const mypage = document.querySelector('.mypage');
  document.addEventListener('click', function clickOutside(event) {
    if (event.target.closest('.mypage')) return;
    setpopover(false);
  });

  // popover 띄우는 함수
  function openPopover() {
    setpopover(true);
  }

  return (
    <div>
      <div className="web-navbar">
        {loginRedux.isLoggedin}
        <div className="logo-bar">
          <Link to="home">
            <img className="logo-image" src={logo} alt="로고" />
          </Link>
          {isLoggedin ? (
            <div className="web-navbar-icon">
              <MdAccessAlarm />
              <MdAccountCircle className="mypage" onClick={openPopover} />
            </div>
          ) : (
            <Link to="login">
              <AiOutlineLogin /> Login
            </Link>
          )}

          {popover ? (
            <div className="popover-box">
              <p>로그아웃</p>
              <Link to="mypage">
                <p>마이페이지</p>
              </Link>
            </div>
          ) : null}
        </div>
        <div className="menu-bar">
          <div className="menu-link">
            <Link to="home" className="btn-link">
              Home
            </Link>
            <Link to="OOTD" className="btn-link">
              OOTD
            </Link>
            <Link to="styling" className="btn-link">
              Styling
            </Link>
            <Link to="Community" className="btn-link">
              Community
            </Link>
          </div>
        </div>
      </div>
      <div className="mobile-navbar">
        <div>
          <Link to="home" className="btn-link">
            <AiOutlineHome />
            <p>홈</p>
          </Link>
        </div>
        <Link to="OOTD" className="btn-link">
          <BsImages />
          <p>OOTD</p>
        </Link>
        <Link to="styling" className="btn-link">
          <IoShirtOutline />
          <p>스타일링</p>
        </Link>
        <Link to="community" className="btn-link">
          <FaRegComments />
          <p>커뮤니티</p>
        </Link>
        <Link to="mypage" className="btn-link">
          <FiUser />
          <p>내정보</p>
        </Link>
      </div>
    </div>
  );
}

export default Nav;
