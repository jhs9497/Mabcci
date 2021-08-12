import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import { HiMenu } from 'react-icons/hi';
import MabcciReview from './MabcciReview';
import MyPageFeed from './MyPageFeed';
import MyPageProfile from './MyPageProfile';
import MypageReadApi from '../../../../../API/MypageAPI/MypageReadApi';
import FollowBox from '../MyPageFollow/FollowBox';
import MyChatList from '../MyPageSetting/MyChatList/MyChatList';
import MyProposalList from '../MyPageSetting/MyProposal/MyProposalList';
import MyProposalListMobile from '../MyPageSetting/MyProposal/MyProposalListMobile';
import MyPageMobileMenu from '../MyPageSetting/MySetting/MyPageMobileMenu';
import MyPageUpdate from '../MyPageSetting/MySetting/MyPageUpdate';

function MyPageMain() {
  const { nickname } = useParams();

  const [myInfo, setMyInfo] = useState({});

  const [followBox, setFollowBox] = useState('none');

  const [chatBox, setChatBox] = useState(false);

  const [proposalBox, setProposalBox] = useState(false);

  const [mobileMenu, setMobileMenu] = useState(false);

  const [myPageUpdate, setMyPageUpdate] = useState('none');

  useEffect(async () => {
    const res = await MypageReadApi(nickname);
    setMyInfo(res.myInfo);
  }, []);

  const goToMobileMenu = () => {
    setMobileMenu(true);
  };

  return (
    <div className="mypage-entire">
      <MyPageUpdate
        myPageUpdate={myPageUpdate}
        setMyPageUpdate={setMyPageUpdate}
      />
      <FollowBox followBox={followBox} setFollowBox={setFollowBox} />
      <MyChatList chatBox={chatBox} setChatBox={setChatBox} />
      <MyProposalList
        proposalBox={proposalBox}
        setProposalBox={setProposalBox}
      />
      <MyProposalListMobile
        proposalBox={proposalBox}
        setProposalBox={setProposalBox}
        mobileMenu={mobileMenu}
        setMobileMenu={setMobileMenu}
      />
      {myInfo ? (
        <MyPageMobileMenu
          myInfo={myInfo}
          setMyInfo={setMyInfo}
          mobileMenu={mobileMenu}
          setMobileMenu={setMobileMenu}
          proposalBox={proposalBox}
          setProposalBox={setProposalBox}
          myPageUpdate={myPageUpdate}
          setMyPageUpdate={setMyPageUpdate}
        />
      ) : null}
      <div className="mypage-container">
        <button
          className="mypage-mobile-setting"
          type="submit"
          onClick={goToMobileMenu}
        >
          <HiMenu />
        </button>
        {myInfo.categories ? (
          <MyPageProfile
            myInfo={myInfo}
            followBox={followBox}
            setFollowBox={setFollowBox}
            chatBox={chatBox}
            setChatBox={setChatBox}
            proposalBox={proposalBox}
            setProposalBox={setProposalBox}
            myPageUpdate={myPageUpdate}
            setMyPageUpdate={setMyPageUpdate}
            setMobileMenu={setMobileMenu}
          />
        ) : null}
        {myInfo.ootds ? <MyPageFeed myInfo={myInfo} /> : null}
        <MabcciReview />
      </div>
    </div>
  );
}

export default MyPageMain;
